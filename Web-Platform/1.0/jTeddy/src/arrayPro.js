define(function () {
//拓展Array方法

	/**
	 * 判断两对象是否相等
	 * @param a
	 * @param b
	 * @returns {boolean}
	 */
	function isObjectValueEqual(a, b) {
		if (typeof a !== typeof  b) {
			return false;
		}
		for (pro in a) {
			if (a[pro] !== b[pro]) {
				return false;
			}
		}
		return true;
	}

	Array.prototype.get = function (num) {
		if (typeof num !== "number") {
			return this;
		}
		return this[num];
	};
	/**
	 * 返回某个指定的元素值在数组中首次出现的位置
	 *
	 * @param {T} el
	 * @return {Number} 指定的元素值在数组中首次出现的位置
	 */
	Array.prototype.indexOf = function (el) {
		if (typeof  el === "object") {
			for (var i = 0; i < this.length; ++i) {
				if (isObjectValueEqual(this[i], el)) {
					return i;
				}
			}
		} else {
			for (var i = 0; i < this.length; ++i) {
				if (this[i] == el) {
					return i;
				}
			}
		}

		return -1;
	};
//删除指定元素
	Array.prototype.removeEl = function (el) {
		if (typeof  el === "object") {
			for (var i = 0; i < this.length; ++i) {
				if (isObjectValueEqual(this[i], el)) {
					this.splice(i, 1);
					i--;
				}
			}
		} else {
			for (var i = 0; i < this.length; ++i) {
				if (this[i] == el) {
					this.splice(i, 1);
					i--;
				}
			}
		}
		return this;
	};

//删除指定下标的元素
	Array.prototype.removeByIndex = function (i) {
		if (typeof i !== "number") {
			try {
				i = parseInt(i);
			} catch (e) {
				return this;
			}
		}
		if (i > -1) {
			this.splice(i, 1);
		}
		return this;
	};

//清空数组
	Array.prototype.clear = function () {
		this.length = 0;
		return this;
	};

	//是否包含
	Array.prototype.contains = function (el) {
		if (this.indexOf(el) > -1) {
			return true;
		}
		return false;
	};

//数组去重
	Array.prototype.unique = function () {
		var r = []; // n为hash表，r为临时数组
		// 遍历当前数组
		jTeddy.each(this, function (i, name) {
			if (!r.contains(name)) {
				r.push(name); // 把当前数组的当前项push到临时数组里面
			}
		});
		return r;
	};


//取交集
	Array.prototype.intersection = function (arr1) {
		var r = [];
		var b = this;
		jTeddy.each(arr1, function (i, o) {
			if (b.contains(o)) {
				r.push(o);
			}
		});
		return r.unique();
	};

//取并集
	Array.prototype.unionset = function (arr1) {
		return (this.concat(arr1)).unique();
	};

//取差集
	Array.prototype.diffset = function (arr1) {
		var r = this.unique(), d = this.intersection(arr1);
		jTeddy.each(d, function (i, o) {
			r.removeEl(o);
		});
		return r.unique();
	};

//取补集
	Array.prototype.suppset = function (arr1) {
		//b是否为a的真子集
		function isPropersubset(a, b) {
			var r = b.clone();
			for (var i = 0; i < r.length; i++) {
				if (a.contains(r[i])) {
					r.removeByIndex(i);
					i--;
				}
			}
			if (r.length) { //length > 0
				return false;
			}
			return true;
		}

		if (!isPropersubset(this, arr1)) {
			return null;
		}
		return this.diffset(arr1).unique();
	};

//克隆
	Array.prototype.clone = function () {
		return Array.prototype.slice.call(this, 0);
	};

//去掉数组中的 undefined 和 null
	Array.prototype.compact = function () {
		for (var i = 0; i < this.length; i++) {
			if (typeof this[i] === "undefined" || this[i] === null) {
				this.removeByIndex(i);
				i--;
			}
		}
		return this;
	};

//合并数组
	Array.prototype.flatten = function () {
		//合并数组
		function makeFlatten(arr, arr1) {
			jTeddy.each(arr, function (i, o) {
				if (jTeddy.isArray(o)) {
					makeFlatten(o, arr1);
				} else {
					arr1.push(o);
				}
			});
			return arr1;
		}

		var r = makeFlatten(this, []);
		return r;
	};

//取最大值
	Array.prototype.max = function (callback) {
		try {
			if (typeof callback === 'function') {
				return callback.call(this, this);
			} else {
				var max = this[0];
				var len = this.length;
				for (var i = 1; i < len; i++) {
					if (this[i] > max) {
						max = this[i];
					}
				}
				return max;
			}
		} catch (e) {
			return null;
		}
	};

//取最小值
	Array.prototype.min = function (callback) {
		try {
			if (typeof callback === 'function') {
				return callback.call(this, this);
			} else {
				var min = this[0];
				var len = this.length;
				for (var i = 1; i < len; i++) {
					if (this[i] < min) {
						min = this[i];
					}
				}
				return min;
			}
		} catch (e) {
			return null;
		}
	};

//返回第一个
	Array.prototype.first = function () {
		return this[0];
	};

//返回最后一个
	Array.prototype.last = function () {
		return this[this.length - 1];
	};

//返回对象在数组中的个数
	Array.prototype.countT = function (el, pro) {
		var c = 0;
		if (typeof pro !== 'undefined') { //根据对象的属性进行匹配
			jTeddy.each(this, function (i, o) {
				if (el[pro] == o[pro]) {
					c += 1;
				}
			});
		} else {
			jTeddy.each(this, function (i, o) {
				if (el == o) {
					c += 1;
				}
			});
		}

		return c;
	};

//获取对象在数组中的下标 || 根据对象属性获取对象数组下标
	Array.prototype.getIndex = function (el, pro) {
		if (typeof pro !== 'undefined') {
			var index = -1;
			for (var i = 0; i < this.length; i++) {
				if (this[i][pro] == el[pro]) {
					index = i;
					break;
				}
			}
			return index;
		} else {
			return this.indexOf(el);
		}
	};

//返回元素在数组中最后匹配上的下标，否则返回-1；offset为从数组末尾开始匹配的偏移量。
	Array.prototype.lastIndexOf = function (el, offset, pro) {
		offset = (arguments[1] || 1) < 1 ? 1 : offset;
		var index = -1;
		if (typeof pro !== 'undefined') {
			for (var i = this.length - 1; i >= 0; i--) {
				if (this[i][pro] == el[pro]) {
					if (offset > 1) {
						offset -= 1;
					} else {
						index = i;
						break;
					}
				}
			}
		} else {
			for (var i = this.length - 1; i >= 0; i--) {
				if (this[i] == el) {
					if (offset > 1) {
						offset -= 1;
					} else {
						index = i;
						break;
					}
				}
			}
		}
		return index;
	};

//数组中插入唯一值
	Array.prototype.pushUnique = function (el, pro) {
		if (typeof pro !== 'undefined') {
			if (!this.length) {
				this.push(el);
			} else {
				for (var i = 0; i < this.length; i++) {
					if (this[i][pro] == el[pro]) {
						break;
					} else if (i == this.length - 1) {
						this.push(el);
						break;
					}
				}
			}
		} else {
			if (!this.contains(el)) {
				this.push(el);
			}
		}
		return this;
	};

//向数组的最开始插入元素，如果该元素在存在相同值，则不插入；反之，则在数组最开始插入元素；
	Array.prototype.unShiftUnique = function (el, pro) {
		if (typeof pro !== 'undefined') {
			if (!this.length) {
				this.unshift(el);
			} else {
				for (var i = 0; i < this.length; i++) {
					if (this[i][pro] == el[pro]) {
						break;
					} else if (i == this.length - 1) {
						this.unshift(el);
						break;
					}
				}
			}
		} else {
			if (!this.contains(el)) {
				this.unshift(el);
			}
		}
		return this;
	};

});



