define(function () {

	String.prototype.toArray = function () {
		return [this];
	};

	//拓展String方法
	String.fn = {
		/**
		 * 字符串str长度超过len，截取0到len字符，并在结尾添加v
		 */
		formatterstr: function (str, len, v) {
			if (str === undefined || str === null || str === "null") {
				return "";
			}
			v = v || "...";
			len = len || 15;
			if (str) {
				var l = str.length;
				if (l > len) {
					str = str.substring(0, len) + v;
				}
			}
			return str;
		},
		/**
		 * 格式化val，当超过c位时，在c处添加s
		 */
		formatter: function (val, c, s) {
			if (val === undefined || val === null) {
				return "";
			}

			c = c || 7;
			s = s || '\n';
			var len = val.length;
			var l = 0;
			if (len >= c) {
				l = len % c == 0 ? len / c : parseInt(len / c) + 1;
			} else {
				return val;
			}
			var r = "";
			for (var i = 0; i < l; i++) {
				if ((i + 1) * c <= val.length) {
					r += val.substring(i * c, (i + 1) * c) + s;
				} else {
					r += val.substring(i * c, (i + 1) * c);
				}
			}
			return r;
		},
		/**
		 * 初始化表单时null值处理
		 *
		 * @param {}
		 *            value
		 * @return {String}
		 */
		nullToStr: function (value) {
			if (value == null || value == "null") {
				return "";
			}
			return value;
		},
		/**
		 * 清除字符串两边的空格
		 *
		 * @param {string}
		 *            value 需要清除的字符串
		 */
		trim: function (value) {
			return jTeddy.trim(value);
		},
		/**
		 * 转义html标签
		 *
		 * @param {String}
		 *            str 需要转义的字符串
		 * @return {String}
		 */
		encodeHTML: function (str) {
			var value = "";
			if (str && str != "") {
				value = (str + "").replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(
					/>/g, '&gt;').replace(/\"/g, '&quot;').replace(/\'/g, '&#39;');
			}
			return value;
		},
		/**
		 * 还原html标签
		 *
		 * @param {String}
		 *            str 需要还原的字符串
		 * @return {String}
		 */
		decodeHTML: function (str) {
			var value = "";
			if (str && str != "") {
				value = str.replace(/&amp;/g, '&').replace(/&lt;/g, '<').replace(
					/&gt;/g, '>').replace(/&quot;/g, '"').replace(/&#39;/g, "'");
			}
			return value;
		},
		/**
		 * 是否完全为空，如包含空格返回 false
		 */
		isEmpty: function (val) {
			return val.length > 0 ? false : true;
		},
		/**
		 * 判断字符串是否为空或者只包含空格。
		 */
		isBlank: function (val) {
			val = String.fn.trim(val);
			return val.length > 0 ? false : true;
		},
		/**
		 * toggle：判断目标值是否为第一个值，否则赋给它第二个对象的值。
		 */
		toggle: function (val, v1, v2) {
			return val === v1 ? v1 : v2;
		}
	};
});
