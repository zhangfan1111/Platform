module("Module String");

test("基本验证", function () {
	expect(10);

	ok(String.prototype.toArray, "String.prototype.toArray()");
	ok(String.fn.formatterstr, "String.fn.formatterstr()");
	ok(String.fn.formatter, "String.fn.formatter()");
	ok(String.fn.nullToStr, "String.fn.nullToStr()");
	ok(String.fn.trim, "String.fn.trim()");
	ok(String.fn.encodeHTML, "String.fn.encodeHTML()");
	ok(String.fn.decodeHTML, "String.fn.decodeHTML()");
	ok(String.fn.isEmpty, "String.fn.isEmpty()");
	ok(String.fn.isBlank, "String.fn.isBlank()");
	ok(String.fn.toggle, "String.fn.toggle()");
});

test("toArray()", function () {
	expect(1);

	deepEqual("name".toArray(), ["name"], "the result is ['name']");
});

test("fn.formatterstr()", function () {
	expect(3);

	var str = "北京时间4月16日凌晨，2014-15赛季欧冠(微博 专题) 1/4决赛首回合，巴塞罗那(官方微博 数据) 客场3比1击败巴黎圣日耳曼(微博 数据) 。";

	var s = '北京时间4月16日凌晨，201...';
	deepEqual(String.fn.formatterstr(str), s, "the result is " + s);

	s = "北京时间4月16日凌晨，2014-15赛季欧冠(微博 专题)...";
	deepEqual(String.fn.formatterstr(str, 30), s, "the result is " + s);

	s = "北京时间4月16日凌晨，2014-15赛季欧冠(微博 专题)***";
	deepEqual(String.fn.formatterstr(str, 30, '***'), s, "the result is " + s);
});

test("fn.formatter()", function () {
	expect(4);

	var str = "1234567890";

	deepEqual(String.fn.formatter(str, 6, '*'), "123456*7890", "the result is '123456*7890'");
	deepEqual(String.fn.formatter(str, 5, '*'), "12345*67890*", "the result is '12345*67890*'");
	deepEqual(String.fn.formatter(str, 10, '*'), "1234567890*", "the result is '1234567890*'");
	deepEqual(String.fn.formatter(str, 11, '*'), "1234567890", "the result is '1234567890'");
});

test("fn.nullToStr()", function () {
	expect(4);

	var s = null;
	var str = "null";

	strictEqual(String.fn.nullToStr(s), "", "null to ''");
	strictEqual(String.fn.nullToStr(str), "", "'null' to ''");

	notStrictEqual(String.fn.nullToStr(s + " "), "", "'null ' is not to '', because it contains space");
	strictEqual(String.fn.nullToStr(jTeddy.trim(s)), "", "null to ''");
});

test("fn.trim()", function () {
	expect(5);

	var str = " aa";

	strictEqual(String.fn.trim(str), "aa", "the result is 'aa'");

	str = "aa ";
	strictEqual(String.fn.trim(str), "aa", "the result is 'aa'");

	str = " aa ";
	strictEqual(String.fn.trim(str), "aa", "the result is 'aa'");

	str = " a a ";
	strictEqual(String.fn.trim(str), "a a", "the result is 'a a'");
	notStrictEqual(String.fn.trim(str), "aa", "the result is 'a a', is not 'aa'");
});

test("fn.isEmpty/isBlank()", function () {
	expect(9);

	var str = "";

	equal(String.fn.isEmpty(str), true, "str is empty");
	equal(String.fn.isBlank(str), true, "str is balnk");

	str = "  ";
	equal(String.fn.isEmpty(str), false, "str is not empty");
	equal(String.fn.isBlank(str), true, "str is balnk");

	str = "  ";
	equal(String.fn.isEmpty(String.fn.trim(str)), true, "String.fn.trim(str) is empty");

	str = 'undefined';
	equal(String.fn.isEmpty(str), false, "'undefined' is not empty");
	equal(String.fn.isBlank(str), false, "'undefined' is not balnk");

	str = 'null';
	equal(String.fn.isEmpty(str), false, "'null' is not empty");
	equal(String.fn.isBlank(str), false, "'undefined' is not balnk");
});

test("fn.toggle()", function () {
	expect(4);

	var str = "asc";
	var v1 = "asc";
	var v2 = "desc";

	deepEqual(String.fn.toggle(str, v1, v2), "asc", "the result is 'asc'");

	str = "aa";
	deepEqual(String.fn.toggle(str, v1, v2), "desc", "the result is 'desc'");

	str = "1";
	v1 = 1;
	notDeepEqual(String.fn.toggle(str, v1, v2), 1, "the result is 'desc', 1 !== '1'");
	deepEqual(String.fn.toggle(str, v1, v2), "desc", "the result is 'desc'");
});
