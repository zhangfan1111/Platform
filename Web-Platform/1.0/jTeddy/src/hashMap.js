define([
	"./core"
], function (jTeddy) {

	HashMap = function () {
		/**
		 * map节点结构体
		 * @param {String} key 键名
		 * @param {Object} value 键值
		 */
		var struct = function (key, value) {
			this.key = key;
			this.value = value;
		};
		/**
		 * 向map中添加一个对象
		 * @param {String} key 键名
		 * @param {Object} value 键值
		 */
		var put = function (key, value) {
			for (var i = 0; i < this.arr.length; i++) {
				if (this.arr[i].key === key) {
					this.arr[i].value = value;
					return;
				}
			}
			this.arr[this.arr.length] = new struct(key, value);
		};
		/**
		 * 获取指定键名的对象
		 * @param {String} key 键名
		 * @return {Object} 未找到对应对象时，返回null
		 */
		var get = function (key) {
			for (var i = 0; i < this.arr.length; i++) {
				if (this.arr[i].key === key) {
					return this.arr[i].value;
				}
			}
			return null;
		};
		/**
		 * 遍历Map数据
		 * @param fn 遍历回调函数
		 *              回调函数参数:key,value
		 *            例：amap.each(function(key,value){ do somethind with key|value });
		 */
		var each = function (fn) {
			for (var i = 0; i < this.arr.length; i++) {
				var entry = this.arr[i];
				fn(entry.key, entry.value);
			}
		};
		/**
		 * 删除指定键名的对象
		 * @param {String} key 键名
		 */
		var remove = function (key) {
			var v;
			for (var i = 0; i < this.arr.length; i++) {
				v = this.arr.pop();
				if (v.key === key) {
					continue;
				}
				this.arr.unshift(v);
			}
		};
		/**
		 * 清楚所有元素
		 */
		var clear = function () {
			this.arr.splice(0, this.arr.length);
		};
		/**
		 * 获取map中保存的对象数
		 * @return {Number} map中已保存值得个数
		 */
		var size = function () {
			return this.arr.length;
		};
		/**
		 * 测试map是否为空
		 * @return {Boolean} true-如果为空，否则返回false
		 */
		var isEmpty = function () {
			return this.arr.length <= 0;
		};
		/**
		 * 返回map中所有元素数组
		 * @return {Array} 所有元素数组
		 */
		var toArray = function () {
			var n_arr = new Array();
			this.each(function (k, v) {
				n_arr.push(v);
			});
			return n_arr;
		};
		var getKeys = function () {
			var n_arr = new Array();
			this.each(function (k, v) {
				n_arr.push(k);
			});
			return n_arr;
		};
		/* 内容数组 */
		this.arr = new Array();
		/* 初始化方法调用 */
		this.get = get;
		this.each = each;
		this.put = put;
		this.remove = remove;
		this.clear = clear;
		this.getKeys = getKeys;
		this.size = size;
		this.isEmpty = isEmpty;
		this.toArray = toArray;
	};

	jTeddy.hashMap = function () {
		return new HashMap();
	}

	return jTeddy;
});
