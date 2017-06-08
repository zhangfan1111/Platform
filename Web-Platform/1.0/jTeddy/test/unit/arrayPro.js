module('Module arrayPro');

test("基本验证", function () {
	expect(24);
	ok(Array.prototype.get, "Array.get()");
	ok(Array.prototype.indexOf, "Array.indexOf()");
	ok(Array.prototype.push, "Array.push()");
	ok(Array.prototype.removeEl, "Array.removeEl()");
	ok(Array.prototype.removeByIndex, "Array.removeByIndex()");
	ok(Array.prototype.clear, "Array.clear()");
	ok(Array.prototype.unique, "Array.unique()");
	ok(Array.prototype.contains, "Array.contains()");
	ok(Array.prototype.intersection, "Array.intersection()");
	ok(Array.prototype.unionset, "Array.unionset()");
	ok(Array.prototype.diffset, "Array.diffset()");
	ok(Array.prototype.suppset, "Array.suppset()");
	ok(Array.prototype.clone, "Array.clone()");
	ok(Array.prototype.compact, "Array.compact()");
	ok(Array.prototype.flatten, "Array.flatten()");
	ok(Array.prototype.max, "Array.max()");
	ok(Array.prototype.min, "Array.min()");
	ok(Array.prototype.first, "Array.first()");
	ok(Array.prototype.last, "Array.last()");
	ok(Array.prototype.countT, "Array.countT()");
	ok(Array.prototype.getIndex, "Array.getIndex()");
	ok(Array.prototype.lastIndexOf, "Array.lastIndexOf()");
	ok(Array.prototype.pushUnique, "Array.pushUnique()");
	ok(Array.prototype.unShiftUnique, "Array.unShiftUnique()");
});

test("get()", function () {
	expect(9);

	var arr = ["first", "secend", "third", "fourth"];

	deepEqual(arr.get(), ["first", "secend", "third", "fourth"], "Return all Elements");
	notEqual(arr.get("1"), "secend", "arg is String");
	deepEqual(arr.get("1"), ["first", "secend", "third", "fourth"], "arg is String, return all Elements");
	equal(arr.get(-1), null, "Over Size get null");
	equal(arr.get(-1), undefined, "Over Size get undefined");
	equal(arr.get(4), null, "Over Size get null");
	equal(arr.get(4), undefined, "Over Size get undefined");
	notEqual(arr.get(4), "null", "Over Size get null, not 'null'");
	notEqual(arr.get(4), "undefined", "Over Size get undefined, not 'undefined'");
});

test("indexOf()", function () {
	expect(7);

	var arr = ["first", "secend", "3", "fourth"];
	var arrObj = [{name: "Tom", age: 18}, {name: "jack", age: 20}, {name: "Lucy", age: 21}];
	var rObj = {name: "jack", age: 20};
	var bObj = [1, "secend", {name: "jack", age: 20}];

	deepEqual(arr.indexOf("first"), 0, "The indexof is 0");
	deepEqual(arr.indexOf("iifirst"), -1, "The indexof is -1");
	notDeepEqual(arr.indexOf(3), -1, "The indexof is 2, 3 == '3'");
	deepEqual(arr.indexOf(3), 2, "The indexof is 2, 3 == '3'");
	deepEqual(arrObj.indexOf(rObj), 1, "The indexof is 1");
	deepEqual(arrObj.indexOf({
		name: "jack",
		age: 20
	}), 1, "The indexof is 1, { age: 20, name: 'jack'} == {name: 'jack', age: 20}");
	deepEqual(bObj.indexOf({
		name: "jack",
		age: 20
	}), 2, "The indexof is 2");
});

test("removeEl", function () {
	expect(8);

	var arr = ["first", "secend", "3", "fourth"];
	var arrObj = [{name: "Tom", age: 18}, {name: "jack", age: 20}, {name: "Lucy", age: 21}];
	var rObj = {name: "jack", age: 20};
	var bArr = [1, '1', 1, 2];
	var cObj = [1, {name: "jack", age: 20}];

	deepEqual(arr.removeEl(3).length, 3, "3 == '3'");

	arr = ["first", "secend", "3", "fourth"];
	deepEqual(arr.removeEl('3'), ["first", "secend", "fourth"], "'3' id removed");

	arr = ["first", "secend", "3", "fourth"];
	equal(arr.removeEl('3').length, 3, "'3' id removed, length is 3");

	deepEqual(bArr.removeEl(1), [2], "result is [2]'");

	equal(arrObj.removeEl({age: 20, name: "jack"}).length, 2, "{ age: 20, name: 'jack'} is removed");

	arrObj = [{name: "Tom", age: 18}, {name: "jack", age: 20}, {name: "Lucy", age: 21}];
	deepEqual(arrObj.removeEl(rObj), [{name: "Tom", age: 18}, {
		name: "Lucy",
		age: 21
	}], "{name: 'jack', age: 20} is removed");

	deepEqual(cObj.removeEl(1), [{name: "jack", age: 20}], "result is [{name: 'jack'', age: 20}]'");

	cObj = [1, {name: "jack", age: 20}];
	deepEqual(cObj.removeEl(rObj), [1], "result is [1]'");
});

test("removeByIndex()", function () {
	expect(5);

	var arr = ["first", "secend", "third", "fourth"];

	deepEqual(arr.removeByIndex(-1), arr, "-1 is over size");

	arr = ["first", "secend", "third", "fourth"];
	deepEqual(arr.removeByIndex(4), arr, "4 is over size");

	arr = ["first", "secend", "third", "fourth"];
	deepEqual(arr.removeByIndex('-1'), arr, "'-1' is over size, arg is '-1'");

	arr = ["first", "secend", "third", "fourth"];
	deepEqual(arr.removeByIndex(1).length, 3, "'secend' is removed");

	arr = ["first", "secend", "third", "fourth"];
	deepEqual(arr.removeByIndex('1').length, 3, "'secend' is removed, arg is '1'");
});

test("clear()", function () {
	expect(2);

	var arr = ["first", "secend", "third", "fourth"];

	equal(arr.clear().length, 0, "arr is clear");

	arr = ["first", "secend", "third", "fourth"];
	deepEqual(arr.clear(), [], "arr is clear, return []");
});

test("unique()", function () {
	expect(3);

	var arr = ["first", "secend", "third", "fourth", 2, 3, "3", 2];
	var arrObj = [{name: "Tom", age: 18}, {name: "jack", age: 20}, {name: "Lucy", age: 21}, {name: "jack", age: 20}];
	var bObj = [1, "1", {name: "jack", age: 20}, {name: "Lucy", age: 21}, {name: "jack", age: 20}];

	deepEqual(arr.unique().length, 6, "arr is diffreence with each other");
	deepEqual(arrObj.unique().length, 3, "arrObj is diffreence with each other");
	deepEqual(bObj.unique().length, 3, "bObj is diffreence with each other");
});

test("intersection()", function () {
	expect(4);

	var aArr = [1, 3, 5, 7, '3'];
	var bArr = [3, 4, '7', 8, 7];
	var arrObj = [{name: "Tom", age: 18}, {age: 20, name: "jack"}, {name: "Lucy", age: 21}];
	var bObj = [1, "1", {name: "jack", age: 20}, {name: "Lucy", age: 21}, {name: "jack", age: 20}];

	notDeepEqual(aArr.intersection(bArr), [3, 7], " the intersection is [3, '7'],is not [3, 7]");
	deepEqual(aArr.intersection(bArr), [3, '7'], " the intersection is [3, '7']");
	deepEqual(arrObj.intersection(bObj), [{name: "jack", age: 20}, {
		name: "Lucy",
		age: 21
	}], " the intersection is [{name: 'jack', age: 20}, {name: 'Lucy', age: 21}]");
	deepEqual(bObj.intersection(arrObj), [{age: 20, name: "jack"}, {
		name: "Lucy",
		age: 21
	}], " the intersection is [{age: 20, name: 'jack'}, {name: 'Lucy', age: 21}]");
});

test("unionset()", function () {
	expect(5);

	var aArr = [1, 3, 5, 7, '3'];
	var bArr = [3, 4, '7', 8, 7];
	var arrObj = [{name: "Tom", age: 18}, {age: 20, name: "jack"}, {name: "Lucy", age: 21}];
	var bObj = [1, "1", {name: "jack", age: 20}, {name: "Lucy", age: 21}, {name: "jack", age: 20}];

	deepEqual(aArr.unionset(bArr).length, 6, " the length is 6");
	deepEqual(aArr.unionset(bArr), [1, 3, 5, 7, 4, 8], " the unionset is [1, 3, 5, 7, 4, 8]");
	deepEqual(bArr.unionset(aArr), [3, 4, '7', 8, 1, 5], " the unionset is [3, 4, '7', 8, 1, 5]");
	deepEqual(arrObj.unionset(bObj).length, 4, " the length is 4");
	deepEqual(arrObj.unionset(bObj), [{name: "Tom", age: 18}, {age: 20, name: "jack"}, {
		name: "Lucy",
		age: 21
	}, 1], " the unionset is difference each other")
});

test("diffset()", function () {
	expect(4);

	var aArr = [1, 3, 5, 7, '3'];
	var bArr = [3, 4, '7', 8, 7];
	var bObj = [1, "1", {name: "jack", age: 20}, {name: "Lucy", age: 21}];

	deepEqual(aArr.diffset(bArr), [1, 5], " the diffset is [1, 5]");
	deepEqual(bArr.diffset(aArr), [4, 8], " the diffset is [4, 8]");
	deepEqual(aArr.diffset(bObj), [3, 5, 7], " the diffset is [3, 5, 7]");
	deepEqual(bObj.diffset(aArr), [{name: "jack", age: 20}, {
		name: "Lucy",
		age: 21
	}], " the diffset is [{name: 'jack', age: 20}, {name: 'Lucy', age: 21}]");
});

test("suppset()", function () {
	expect(4);

	var aArr = [1, 3, 5, 7, '3'];
	var bArr = [3, 4, '7', 8, 7];
	var cArr = [3, '7', 7];

	var aObj = [1, "1", {name: "jack", age: 20}, {name: "Lucy", age: 21}];
	var bObj = ["1", {name: "jack", age: 20}];

	deepEqual(aArr.suppset(bArr), null, "bArr is not proper subset of aArr");
	deepEqual(aArr.suppset(cArr), [1, 5], "the proper subset is [1, 5]");

	deepEqual(bObj.suppset(aObj), null, "bObj is not proper subset of aObj");
	deepEqual(aObj.suppset(bObj), [{name: "Lucy", age: 21}], "the proper subset is [{name: 'Lucy', age: 21}]");
});

test("clone()", function () {
	expect(6);

	var aArr = [1, 3, 5, 7];

	deepEqual(aArr.clone(), [1, 3, 5, 7], "the result is clone Object");
	deepEqual(aArr.clone(), aArr, "the result is clone Object");

	notStrictEqual(aArr.clone(), [1, 3, 5, 7], "the result is clone Object, but is not strict equal of aArr");
	notStrictEqual(aArr.clone(), aArr, "the result is clone Object, but is not strict equal of aArr");

	notEqual(aArr.clone(), [1, 3, 5, 7], "the result is clone Object, but is not equal of aArr");
	notEqual(aArr.clone(), aArr, "the result is clone Object, but is not equal of aArr");
});

test("compact()", function () {
	expect(5);

	var aArr = [1, 3, 5, 7, undefined];

	deepEqual(aArr.compact(), [1, 3, 5, 7], "undefined is removed");

	aArr = [1, 3, 5, 7, null];
	deepEqual(aArr.compact(), [1, 3, 5, 7], "null is removed");

	aArr = [1, 3, 5, 7, null, undefined];
	deepEqual(aArr.compact(), [1, 3, 5, 7], "null and undefined is removed");

	aArr = [1, 3, 5, 7, 'undefined'];
	deepEqual(aArr.compact(), [1, 3, 5, 7, 'undefined'], " 'undefined' !== undefined");

	aArr = [1, 3, 5, 7, 'null'];
	deepEqual(aArr.compact(), [1, 3, 5, 7, 'null'], " 'null' !== null");
});

test("flatten()", function () {
	expect(3);

	var arr = [1, 2, ['3', '4', [5, 6], 7], 8];

	deepEqual(arr.flatten(), [1, 2, '3', '4', 5, 6, 7, 8], "the result is [1, 2, '3', '4', 5, 6, 7, 8]");

	arr = [[1, 2], ['3', '4', [5, 6], 7], 8];
	deepEqual(arr.flatten(), [1, 2, '3', '4', 5, 6, 7, 8], "the result is [1, 2, '3', '4', 5, 6, 7, 8]");

	arr = [[[1, 2], ['3', '4', [5, 6], 7], 8]];
	deepEqual(arr.flatten(), [1, 2, '3', '4', 5, 6, 7, 8], "the result is [1, 2, '3', '4', 5, 6, 7, 8]");
});

test("max()", function () {
	expect(4);

	var arr = [1, 2, '3', 4, '5'];

	equal(arr.max(), 5, "the max is '5'");

	arr = [1, 2, '3', 4, 'a'];
	equal(arr.max(), 4, "the max is '4'");

	arr = ['b', 'g', 'a'];
	equal(arr.max(), 'g', "the max is 'g'");

	arr = [{name: "jack", age: 20}, {name: "Lucy", age: 21}];
	function callbackfun(obj) {
		return obj[1];
	}

	deepEqual(arr.max(callbackfun), {name: "Lucy", age: 21}, "the max is '{name: 'Lucy', age: 21}'");
});

test("min()", function () {
	expect(4);

	var arr = [1, 2, '3', 4, '5'];

	equal(arr.min(), 1, "the min is 1");

	arr = [1, 2, '3', 4, 'a'];
	equal(arr.min(), 1, "the min is 1");

	arr = ['b', 'g', 'a'];
	equal(arr.min(), 'a', "the min is 'a'");

	arr = [{name: "jack", age: 20}, {name: "Lucy", age: 21}];
	function callbackfun(obj) {
		return obj[1];
	}

	deepEqual(arr.min(callbackfun), {name: "Lucy", age: 21}, "the min is '{name: 'Lucy', age: 21}'");
});

test("first()", function () {
	expect(6);

	var arr = [1, 2, '3', 4, '5'];

	deepEqual(arr.first(), 1, "the result is 1");

	arr = [undefined, 2, '3', 4, '5'];
	deepEqual(arr.first(), undefined, "the result is undefined");
	notDeepEqual(arr.first(), 'undefined', "the result is not 'undefined'");

	arr = [null, 2, '3', 4, '5'];
	deepEqual(arr.first(), null, "the result is null");
	notDeepEqual(arr.first(), undefined, "the result is not undefined");

	arr = [];
	deepEqual(arr.first(), undefined, "array is empty, the result is undefined");
});

test("last()", function () {
	expect(6);

	var arr = [1, 2, '3', 4, '5'];

	deepEqual(arr.last(), '5', "the result is '5'");

	arr = [2, '3', 4, '5', undefined];
	deepEqual(arr.last(), undefined, "the result is undefined");
	notDeepEqual(arr.last(), 'null', "the result is not 'null'");

	arr = [null, 2, '3', 4, '5', null];
	deepEqual(arr.last(), null, "the result is null");
	deepEqual(arr.last(), null, "the result is not undefined");

	arr = [];
	deepEqual(arr.last(), undefined, "array is empty, the result is undefined");
});

test("countT()", function () {
	expect(7);

	var arr = [1, 2, 3, 4, '3'];

	deepEqual(arr.countT(3), 2, "the result is 2");

	arr = [1, 2, undefined, undefined, '3'];
	deepEqual(arr.countT(undefined), 2, "the result is 2");

	arr = [1, 2, null, 'null', '3'];
	deepEqual(arr.countT(null), 1, "the result is 1, null !== 'null'");

	arr = [1, 2, undefined, 'undefined', '3'];
	deepEqual(arr.countT(undefined), 1, "the result is 1, undefined !== 'undefined'");

	arr = [{name: "jack", age: 20}, {name: "Lucy", age: 20}];
	deepEqual(arr.countT({name: "Tom", age: 20}, 'age'), 2, "the result is 2, dependence by 'age'");
	deepEqual(arr.countT({name: "Tom", age: 20}, 'name'), 0, "the result is 0, dependence by 'name'");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Lucy", age: 20}];
	deepEqual(arr.countT({name: "Tom", age: 20}, 'age'), 2, "the result is 2, dependence by 'age'");
});

test("getIndex()", function () {
	expect(9);

	var arr = [1, 2, 3, 4, '3'];

	deepEqual(arr.getIndex(3), 2, "the result is 2");
	deepEqual(arr.getIndex('3'), 2, "the result is 2, 3 == '3'");

	arr = [1, 2, null, 'null', '3'];
	deepEqual(arr.getIndex(null), 2, "the result is 2, null !== 'null'");
	deepEqual(arr.getIndex('null'), 3, "the result is 3, null !== 'null'");

	arr = [1, 2, undefined, 'undefined', '3'];
	deepEqual(arr.getIndex(undefined), 2, "the result is 2, undefined !== 'undefined'");
	deepEqual(arr.getIndex('undefined'), 3, "the result is 3, undefined !== 'undefined'");

	arr = [{name: "jack", age: 21}, {name: "Lucy", age: 20}];
	deepEqual(arr.getIndex({name: "jack", age: 20}, 'age'), 1, "the result is 1, dependence by 'age'");
	deepEqual(arr.getIndex({name: "jack", age: 20}, 'name'), 0, "the result is 0, dependence by 'name'");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Tom", age: 20}];
	deepEqual(arr.getIndex({name: "Tom", age: 20}, 'name'), 4, "the result is 4, dependence by 'name'");
});

test("lastIndexOf()", function () {
	expect(12);

	var arr = [1, 2, 3, 3, '3'];

	deepEqual(arr.lastIndexOf(3), 4, "the result is 4");
	deepEqual(arr.lastIndexOf(3, 2), 3, "the result is 3, offset is 2");
	deepEqual(arr.lastIndexOf(3, 3), 2, "the result is 2, offset is 3");
	deepEqual(arr.lastIndexOf(3, 6), -1, "the result is -1, offset is 6");

	arr = [1, 2, null, 'null', '3'];
	deepEqual(arr.lastIndexOf(null), 2, "the result is 2, null !== 'null'");
	deepEqual(arr.lastIndexOf('null'), 3, "the result is 3, null !== 'null'");
	deepEqual(arr.lastIndexOf(null, 2), -1, "the result is -1, offset is 2");

	arr = [1, 2, undefined, 'undefined', '3'];
	deepEqual(arr.lastIndexOf(undefined), 2, "the result is 2, undefined !== 'undefined'");
	deepEqual(arr.lastIndexOf('undefined'), 3, "the result is 3, undefined !== 'undefined'");
	deepEqual(arr.lastIndexOf(undefined, 2), -1, "the result is -1, offset is 2");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Tom", age: 20}];
	deepEqual(arr.lastIndexOf({name: "Tom", age: 20}, 1, 'name'), 4, "the result is 4, dependence by 'name'");
	deepEqual(arr.lastIndexOf({
		name: "Tom",
		age: 20
	}, 2, 'age'), 3, "the result is 3, dependence by 'age', offset is 2");
});

test("pushUnique()", function () {
	expect(17);

	var arr = [];
	deepEqual(arr.pushUnique(3), [3], "the result is [3]");

	arr = [1, 2, 3, 3, '3'];
	arr.pushUnique('3');
	equal(arr.length, 5, "the length is 5");

	arr.pushUnique(3);
	equal(arr.length, 5, "the length is 5, 3 == '3'");

	arr.pushUnique(4);
	equal(arr.length, 6, "the length is 6");
	deepEqual(arr.last(), 4, "the last is 4");

	arr = [1, 2, null, '3'];
	arr.pushUnique(null);
	equal(arr.length, 4, "the length is 4");
	deepEqual(arr.last(), '3', "the last is nochange");

	arr.pushUnique('null');
	equal(arr.length, 5, "the length is 5");
	deepEqual(arr.last(), 'null', "the last is 'null', null != 'null'");

	arr = [1, 2, undefined, '3'];
	arr.pushUnique(undefined);
	equal(arr.length, 4, "the length is 4");
	deepEqual(arr.last(), '3', "the last is nochange");

	arr.pushUnique('undefined');
	equal(arr.length, 5, "the length is 5");
	deepEqual(arr.last(), 'undefined', "the last is 'undefined', undefined != 'undefined'");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Tom", age: 21}];
	arr.pushUnique({name: "lucy", age: 21}, 'name');
	equal(arr.length, 6, "the length is 6");
	deepEqual(arr.last(), {name: "lucy", age: 21}, "the last is {name: 'jack', age: 21}, dependence by 'name'");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Tom", age: 21}];
	arr.pushUnique({name: "jack", age: 21}, 'name');
	equal(arr.length, 5, "the length is 5");
	deepEqual(arr.last(), {name: "Tom", age: 21}, "the last is {name: 'Tom', age: 21}, dependence by 'name'");
});

test("unShiftUnique()", function () {
	expect(17);

	var arr = [];
	deepEqual(arr.unShiftUnique(3), [3], "the result is [3]");

	arr = [1, 2, 3, 3, '3'];
	arr.unShiftUnique('3');
	equal(arr.length, 5, "the length is 5");

	arr.unShiftUnique(3);
	equal(arr.length, 5, "the length is 5, 3 == '3'");

	arr.unShiftUnique(4);
	equal(arr.length, 6, "the length is 6");
	deepEqual(arr.first(), 4, "the last is 4");

	arr = [1, 2, null, '3'];
	arr.unShiftUnique(null);
	equal(arr.length, 4, "the length is 4");
	deepEqual(arr.first(), 1, "the first is nochange");

	arr.unShiftUnique('null');
	equal(arr.length, 5, "the length is 5");
	deepEqual(arr.first(), 'null', "the first is 'null', null != 'null'");

	arr = [1, 2, undefined, '3'];
	arr.unShiftUnique(undefined);
	equal(arr.length, 4, "the length is 4");
	deepEqual(arr.first(), 1, "the first is nochange");

	arr.unShiftUnique('undefined');
	equal(arr.length, 5, "the length is 5");
	deepEqual(arr.first(), 'undefined', "the first is 'undefined', undefined != 'undefined'");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Tom", age: 21}];
	arr.unShiftUnique({name: "lucy", age: 21}, 'name');
	equal(arr.length, 6, "the length is 6");
	deepEqual(arr.first(), {name: "lucy", age: 21}, "the first is {name: 'jack', age: 21}, dependence by 'name'");

	arr = [1, 3, 20, {name: "jack", age: 20}, {name: "Tom", age: 21}];
	arr.unShiftUnique({name: "jack", age: 21}, 'name');
	equal(arr.length, 5, "the length is 5");
	deepEqual(arr.first(), 1, "the first is 1, dependence by 'name'");
});
