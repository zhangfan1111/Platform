define(function () {
//扩展Date的常量
	Date.CS = {
		//year
		Y: "yyyy",
		//month
		M: "MM",
		//day
		D: "dd",
		//hour 12小时
		LH: "hh",
		//hour 24小时
		H: "HH",
		//minute
		LM: "mm",
		//second
		S: "ss",
		//millisecond
		MS: "S",

		//yyyy-MM-dd hh:mm:ss.S ==> 2006-07-02 08:09:04.423
		SD: "yyyy-MM-dd hh:mm:ss.S",

		//yyyy-M-d h:m:s.S ==> 2006-7-2 8:9:4.18
		PD: "yyyy-M-d h:m:s.S",

		//yyyy-MM-dd hh:mm:ss ==> 2006-07-02 08:09:04
		GD: "yyyy-MM-dd hh:mm:ss",

		//yyyy-MM-dd ==> 2006-07-02
		YD: "yyyy-MM-dd"
	};


	/**
	 * 对Date的扩展，将 Date 转化为指定格式的String * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q)
	 * 可以用 1-2 个占位符 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) * eg: * (new
	 * Date()).pattern("yyyy-MM-dd hh:mm:ss.S")==> 2006-07-02 08:09:04.423
	 * (new Date()).pattern("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
	 */
	Date.prototype.pattern = function (fmt) {
		var o = {
			"M+": this.getMonth() + 1, //月份
			"d+": this.getDate(), //日
			"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
			"H+": this.getHours(), //小时
			"m+": this.getMinutes(), //分
			"s+": this.getSeconds(), //秒
			"q+": Math.floor((this.getMonth() + 3) / 3), //季度
			"S": this.getMilliseconds() //毫秒
		};

		if (/(y+)/.test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		}

		for (var k in o) {
			if (new RegExp("(" + k + ")").test(fmt)) {
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}
		return fmt;
	};

//添加Date.M对象，拓展方法
	Date.fn = {
		/**
		 * 计算时间差
		 * @param {Object} interval:计算类型：D是按照天、H是按照小时、M是按照分钟、S是按照秒、T是按照毫秒
		 * @param {Object} date1:起始日期  格式为年月格式 为2012-06-20 10:10:10
		 * @param {Object} date2:结束日期
		 */
		difference: function (interval, date1, date2) {
			var objInterval = {'D': 1000 * 60 * 60 * 24, 'H': 1000 * 60 * 60, 'M': 1000 * 60, 'S': 1000, 'T': 1};
			interval = interval.toUpperCase();
			var dt1 = Date.parse(date1.replace(/-/g, "/"));
			var dt2 = Date.parse(date2.replace(/-/g, "/"));
			try {
				return ((dt2 - dt1) / objInterval[interval]).toFixed(0);//保留两位小数点
			} catch (e) {
				return e.message;
			}
		},
		/**
		 * between：判断时间是否在两者之间，如果在，返回true，反之，false。
		 * @param：
		 *     Date val：目标时间
		 *     Date start：开始时间
		 *     Date end：结束时间
		 */
		between: function (val, start, end) {
			var dt1 = val.getTime();
			var dt2 = start.getTime();
			var dt3 = end.getTime();

			if (dt1 <= dt3 && dt1 >= dt2) {
				return true;
			}
			return false;
		},
		/**
		 * 参数：
		 * 1：目标日期
		 * 2：增加 或者 减少 的毫秒数：整数增加 || 负数减少
		 * 返回：
		 * Date对象
		 */
		addDate: function (date, mill) {
			try {
				var num = date.getTime();
				num += mill;
				date.setTime(num);
				return date;
			} catch (e) {
				return e.message;
			}
		},
		/**
		 * isEqual：判断两个日期是否相等。如果前一个日期大于后一个日期，则返回1；如果相等，则返回0；如果前一个日期小于后一个日期，则返回-1；
		 * @param：
		 * Date val1：目标日期1；
		 * Date val2：目标日期2；
		 */
		isEqual: function (d1, d2) {
			var n1 = d1.getTime(), n2 = d2.getTime();
			if (n1 == n2) {
				return 0;
			} else if (n1 > n2) {
				return 1;
			} else {
				return -1;
			}
		},
		/**
		 * isLeapYear ： 是否是闰年
		 *  @param number: num 年份
		 */
		isLeapYear: function (num) {
			if ((num % 4 == 0 && num % 100 != 0) || (num % 100 == 0 && num % 400 == 0)) {
				return true;
			}
			return false;
		},
		/**
		 * getYDays：获取当前年的天数，返回数字365 || 366
		 * @param number: num 年份
		 */
		getYDays: function (num) {
			try {
				if (Date.fn.isLeapYear(num)) {
					return 366;
				} else {
					return 365;
				}
			} catch (e) {
				return e.message;
			}
		},
		/**
		 * getMDays：获取当前月的天数，返回28 || 29 || 30 || 31，获取失败，返回0；参数值范围-月为：1~12.
		 * @param:
		 * number month:月份
		 * number year:年份
		 */
		getMDays: function (month, year) {
			if (month > 12 || month < 1) {
				return 0;
			}
            //
			//if (month > 12) {
			//	month -= 12; //月份减
			//	year += 1; //年份增
			//}
			var d = new Date(year, month, 1); //取当年当月中的第一天
			return (new Date(d.getTime() - 1000 * 60 * 60 * 24)).getDate();
		},
		/**
		 * 转化毫秒
		 * 例子：
		 * Date.fn.formatMill(1234435);  return -> {y: 1990, m:10, d: 23, h:10, mi: 34, s:45, ms:234}
		 * @param millisecends
		 */
		formatMill: function (millisecends) {
			if (typeof  millisecends !== "number" || millisecends < 0) {
				return {};
			}
			var date = new Date();
			date.setTime(millisecends);
			return {
				y: date.getFullYear(),
				m: date.getMonth() + 1,
				d: date.getDate(),
				h: date.getHours(),
				mi: date.getMinutes(),
				s: date.getSeconds(),
				ms: date.getMilliseconds()
			};
		}
	};
});

