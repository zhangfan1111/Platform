module("Module Basic Core");

test("基础验证", function () {
	expect(2);

	ok(jTeddy, "jTeddy");
	ok($T, "$T");
});


test("jTeddy对象", function () {
	expect(9);

	deepEqual(jTeddy(), jTeddy, "jTeddy() === jTeddy");
	deepEqual(jTeddy(undefined), jTeddy, "jTeddy(undefined) === jTeddy()");
	deepEqual(jTeddy(null), jTeddy, "jTeddy(null) === jTeddy()");
	deepEqual(jTeddy(""), jTeddy, "jTeddy('') === jTeddy()");
	deepEqual(jTeddy(jTeddy([])).get(), jTeddy([]).get(), "jTeddy(jTeddyObj) == jTeddyObj");

	equal(jTeddy([1, 2, 3]).get(1), 2, "Test passing an array to the factory");
	deepEqual(jTeddy([1, 2, 3]), [1, 2, 3], "jTeddy(Array) == Array");
	deepEqual(jTeddy("123"), "123", "jTeddy(String) == String");
	deepEqual(jTeddy({a: "123"}), {a: "123"}, "jTeddy(Object) == Object");
});

test("trim", function () {
	expect(13);

	var nbsp = String.fromCharCode(160);

	equal(jTeddy.trim("hello  "), "hello", "trailing space");
	equal(jTeddy.trim("  hello"), "hello", "leading space");
	equal(jTeddy.trim("  hello   "), "hello", "space on both sides");
	equal(jTeddy.trim("  " + nbsp + "hello  " + nbsp + " "), "hello", "&nbsp;");

	equal(jTeddy.trim(), "", "Nothing in.");
	equal(jTeddy.trim(undefined), "", "Undefined");
	equal(jTeddy.trim(null), "", "Null");
	equal(jTeddy.trim(5), "5", "Number");
	equal(jTeddy.trim(false), "false", "Boolean");

	equal(jTeddy.trim(" "), "", "space should be trimmed");
	equal(jTeddy.trim("ipad\xA0"), "ipad", "nbsp should be trimmed");
	equal(jTeddy.trim("\uFEFF"), "", "zwsp should be trimmed");
	equal(jTeddy.trim("\uFEFF \xA0! | \uFEFF"), "! |", "leading/trailing should be trimmed");
});

test("type", function () {
	expect(28);

	equal(jTeddy.type(null), "null", "null");
	equal(jTeddy.type(undefined), "undefined", "undefined");
	equal(jTeddy.type(true), "boolean", "Boolean");
	equal(jTeddy.type(false), "boolean", "Boolean");
	equal(jTeddy.type(Boolean(true)), "boolean", "Boolean");
	equal(jTeddy.type(0), "number", "Number");
	equal(jTeddy.type(1), "number", "Number");
	equal(jTeddy.type(Number(1)), "number", "Number");
	equal(jTeddy.type(""), "string", "String");
	equal(jTeddy.type("a"), "string", "String");
	equal(jTeddy.type(String("a")), "string", "String");
	equal(jTeddy.type({}), "object", "Object");
	equal(jTeddy.type(/foo/), "regexp", "RegExp");
	equal(jTeddy.type(new RegExp("asdf")), "regexp", "RegExp");
	equal(jTeddy.type([1]), "array", "Array");
	equal(jTeddy.type(new Date()), "date", "Date");
	equal(jTeddy.type(new Function("return;")), "function", "Function");
	equal(jTeddy.type(function () {
	}), "function", "Function");
	equal(jTeddy.type(new Error()), "error", "Error");
	equal(jTeddy.type(window), "object", "Window");
	equal(jTeddy.type(document), "object", "Document");
	equal(jTeddy.type(document.body), "object", "Element");
	equal(jTeddy.type(document.createTextNode("foo")), "object", "TextNode");
	equal(jTeddy.type(document.getElementsByTagName("*")), "object", "NodeList");

	// Avoid Lint complaints
	var MyString = String,
		MyNumber = Number,
		MyBoolean = Boolean,
		MyObject = Object;
	equal(jTeddy.type(new MyBoolean(true)), "boolean", "Boolean");
	equal(jTeddy.type(new MyNumber(1)), "number", "Number");
	equal(jTeddy.type(new MyString("a")), "string", "String");
	equal(jTeddy.type(new MyObject()), "object", "Object");
});

//测试对象是否是纯粹的对象（通过 "{}" 或者 "new Object" 创建的）
asyncTest("isPlainObject", function () {
	expect(14);

	var pass,
		fn = function () {
		};

	// The use case that we want to match
	ok(jTeddy.isPlainObject({}), "{}");

	// Not objects shouldn't be matched
	ok(!jTeddy.isPlainObject(""), "string");
	ok(!jTeddy.isPlainObject(0) && !jTeddy.isPlainObject(1), "number");
	ok(!jTeddy.isPlainObject(true) && !jTeddy.isPlainObject(false), "boolean");
	ok(!jTeddy.isPlainObject(null), "null");
	ok(!jTeddy.isPlainObject(undefined), "undefined");

	// Arrays shouldn't be matched
	ok(!jTeddy.isPlainObject([]), "array");

	// Instantiated objects shouldn't be matched
	ok(!jTeddy.isPlainObject(new Date()), "new Date");

	// Functions shouldn't be matched
	ok(!jTeddy.isPlainObject(fn), "fn");

	// Again, instantiated objects shouldn't be matched
	ok(!jTeddy.isPlainObject(new fn()), "new fn (no methods)");

	// Makes the function a little more realistic
	// (and harder to detect, incidentally)
	fn.prototype["someMethod"] = function () {
	};

	// Again, instantiated objects shouldn't be matched
	ok(!jTeddy.isPlainObject(new fn()), "new fn");

	// DOM Element
	ok(!jTeddy.isPlainObject(document.createElement("div")), "DOM Element");

	// Window
	ok(!jTeddy.isPlainObject(window), "window");

	pass = false;
	try {
		jTeddy.isPlainObject(window.location);
		pass = true;
	} catch (e) {
	}
	ok(pass, "Does not throw exceptions on host objects");

	//重新启动test测试
	start();
});


test("isFunction", function () {
	expect(19);

	var mystr, myarr, myfunction, fn, obj, nodes, first, input, a;

	// Make sure that false values return false
	ok(!jTeddy.isFunction(), "No Value");
	ok(!jTeddy.isFunction(null), "null Value");
	ok(!jTeddy.isFunction(undefined), "undefined Value");
	ok(!jTeddy.isFunction(""), "Empty String Value");
	ok(!jTeddy.isFunction(0), "0 Value");

	// Check built-ins
	ok(jTeddy.isFunction(String), "String Function(" + String + ")");
	ok(jTeddy.isFunction(Array), "Array Function(" + Array + ")");
	ok(jTeddy.isFunction(Object), "Object Function(" + Object + ")");
	ok(jTeddy.isFunction(Function), "Function Function(" + Function + ")");

	// When stringified, this could be misinterpreted
	mystr = "function";
	ok(!jTeddy.isFunction(mystr), "Function String");

	// When stringified, this could be misinterpreted
	myarr = ["function"];
	ok(!jTeddy.isFunction(myarr), "Function Array");

	// When stringified, this could be misinterpreted
	myfunction = {"function": "test"};
	ok(!jTeddy.isFunction(myfunction), "Function Object");

	// Make sure normal functions still work
	fn = function () {
	};
	ok(jTeddy.isFunction(fn), "Normal Function");

	obj = document.createElement("object");

	// Firefox says this is a function
	ok(!jTeddy.isFunction(obj), "Object Element");

	// Since 1.3, this isn't supported (#2968)
	//ok( jTeddy.isFunction(obj.getAttribute), "getAttribute Function" );

	nodes = document.body.childNodes;

	// Safari says this is a function
	ok(!jTeddy.isFunction(nodes), "childNodes Property");

	first = document.body.firstChild;

	// Normal elements are reported ok everywhere
	ok(!jTeddy.isFunction(first), "A normal DOM Element");

	input = document.createElement("input");
	input.type = "text";
	document.body.appendChild(input);

	// Since 1.3, this isn't supported (#2968)
	//ok( jTeddy.isFunction(input.focus), "A default function property" );

	document.body.removeChild(input);

	a = document.createElement("a");
	a.href = "some-function";
	document.body.appendChild(a);

	// This serializes with the word 'function' in it
	ok(!jTeddy.isFunction(a), "Anchor Element");

	document.body.removeChild(a);

	// Recursive function calls have lengths and array-like properties
	function callme(callback) {
		function fn(response) {
			callback(response);
		}

		ok(jTeddy.isFunction(fn), "Recursive Function Call");

		fn({some: "data"});
	}

	callme(function () {
		callme(function () {
		});
	});
});

// 检测变量是否为数字或数字字符串
test("isNumeric", function () {
	expect(38);

	var t = jTeddy.isNumeric,
		ToString = function (value) {
			this.toString = function () {
				return String(value);
			};
		};

	ok(t("-10"), "Negative integer string");
	ok(t("0"), "Zero string");
	ok(t("5"), "Positive integer string");
	ok(t(-16), "Negative integer number");
	ok(t(0), "Zero integer number");
	ok(t(32), "Positive integer number");
	ok(t("040"), "Octal integer literal string");
	ok(t("0xFF"), "Hexadecimal integer literal string");
	ok(t(0xFFF), "Hexadecimal integer literal");
	ok(t("-1.6"), "Negative floating point string");
	ok(t("4.536"), "Positive floating point string");
	ok(t(-2.6), "Negative floating point number");
	ok(t(3.1415), "Positive floating point number");
	ok(t(1.5999999999999999), "Very precise floating point number");
	ok(t(8e5), "Exponential notation");
	ok(t("123e-2"), "Exponential notation string");
	ok(t(new ToString("42")), "Custom .toString returning number");

	equal(t(""), false, "Empty string");
	equal(t("        "), false, "Whitespace characters string");
	equal(t("\t\t"), false, "Tab characters string");
	equal(t("abcdefghijklm1234567890"), false, "Alphanumeric character string");
	equal(t("xabcdefx"), false, "Non-numeric character string");
	equal(t(true), false, "Boolean true literal");
	equal(t(false), false, "Boolean false literal");
	equal(t("bcfed5.2"), false, "Number with preceding non-numeric characters");
	equal(t("7.2acdgs"), false, "Number with trailling non-numeric characters");
	equal(t(undefined), false, "Undefined value");
	equal(t(null), false, "Null value");
	equal(t(NaN), false, "NaN value");
	equal(t(Infinity), false, "Infinity primitive");
	equal(t(Number.POSITIVE_INFINITY), false, "Positive Infinity");
	equal(t(Number.NEGATIVE_INFINITY), false, "Negative Infinity");
	equal(t(new ToString("Devo")), false, "Custom .toString returning non-number");
	equal(t({}), false, "Empty object");
	equal(t([]), false, "Empty array");
	equal(t([42]), false, "Array with one number");
	equal(t(function () {
	}), false, "Instance of a function");
	equal(t(new Date()), false, "Instance of a Date");
});


test("isWindow", function () {
	expect(13);

	ok(jTeddy.isWindow(window), "window");
	ok(!jTeddy.isWindow(), "empty");
	ok(!jTeddy.isWindow(null), "null");
	ok(!jTeddy.isWindow(undefined), "undefined");
	ok(!jTeddy.isWindow(document), "document");
	ok(!jTeddy.isWindow(document.documentElement), "documentElement");
	ok(!jTeddy.isWindow(""), "string");
	ok(!jTeddy.isWindow(1), "number");
	ok(!jTeddy.isWindow(true), "boolean");
	ok(!jTeddy.isWindow({}), "object");
	ok(!jTeddy.isWindow({
		setInterval: function () {
		}
	}), "fake window");
	ok(!jTeddy.isWindow(/window/), "regexp");
	ok(!jTeddy.isWindow(function () {
	}), "function");
});

test("length", function () {
	expect(3);
	equal(jTeddy([1, 2, 3, 4, 5, 6]).length, 6, "Get Number of Elements Found");
	equal(jTeddy(["234"]).length, 1, "Length : 1");
	equal(jTeddy("234").length, 3, "Length : 3");
});


test("get()", function () {
	expect(4);
	deepEqual(jTeddy([1, 2, 3]).get(), [1, 2, 3], "Get All Elements");
	deepEqual(jTeddy([{a: 1}]).get(), [{a: 1}], "Get All Elements");
	deepEqual(jTeddy([{a: 1}]).get(0), {a: 1}, "Get First Elements");
	deepEqual(jTeddy([{a: 1}]).get(1), undefined, "Get undefined Elements");
});

test("toArray()", function () {
	expect(1);
	deepEqual(jTeddy("123").toArray(), ["123"], "Convert jTeddy object to an Array");
});

test("inArray()", function () {
	expect(1);

	equal(jTeddy.inArray(1, [2, 1, 3], 1), 1, "indexOf : 1");

});

test("each(Function)", function () {
	expect(1);
	var div, pass, i;

	div = [{foo: "123"}, {foo: "435"}];
	jTeddy.each(div, function () {
		this.foo = "zoo";
	});
	pass = true;
	for (i = 0; i < div.length; i++) {
		if (div.get(i).foo !== "zoo") {
			pass = false;
		}
	}
	ok(pass, "Execute a function, Relative");
});

test("jTeddy.map", function () {
	expect(25);

	var i, label, result, callback;

	result = jTeddy.map([3, 4, 5], function (v, k) {
		return k;
	});
	equal(result.join(""), "012", "Map the keys from an array");

	result = jTeddy.map([3, 4, 5], function (v) {
		return v;
	});
	equal(result.join(""), "345", "Map the values from an array");

	result = jTeddy.map({a: 1, b: 2}, function (v, k) {
		return k;
	});
	equal(result.join(""), "ab", "Map the keys from an object");

	result = jTeddy.map({a: 1, b: 2}, function (v) {
		return v;
	});
	equal(result.join(""), "12", "Map the values from an object");

	result = jTeddy.map(["a", undefined, null, "b"], function (v) {
		return v;
	});
	equal(result.join(""), "ab", "Array iteration does not include undefined/null results");

	result = jTeddy.map({a: "a", b: undefined, c: null, d: "b"}, function (v) {
		return v;
	});
	equal(result.join(""), "ab", "Object iteration does not include undefined/null results");

	result = {
		Zero: function () {
		},
		One: function (a) {
			a = a;
		},
		Two: function (a, b) {
			a = a;
			b = b;
		}
	};
	callback = function (v, k) {
		equal(k, "foo", label + "-argument function treated like object");
	};
	for (i in result) {
		label = i;
		result[i].foo = "bar";
		jTeddy.map(result[i], callback);
	}

	result = {
		"undefined": undefined,
		"null": null,
		"false": false,
		"true": true,
		"empty string": "",
		"nonempty string": "string",
		"string \"0\"": "0",
		"negative": -1,
		"excess": 1
	};
	callback = function (v, k) {
		equal(k, "length", "Object with " + label + " length treated like object");
	};
	for (i in result) {
		label = i;
		jTeddy.map({length: result[i]}, callback);
	}

	result = {
		"sparse Array": Array(4),
		"length: 1 plain object": {length: 1, "0": true},
		"length: 2 plain object": {length: 2, "0": true, "1": true},
		NodeList: document.getElementsByTagName("html")
	};
	callback = function (v, k) {
		if (result[label]) {
			delete result[label];
			equal(k, "0", label + " treated like array");
		}
	};
	for (i in result) {
		label = i;
		jTeddy.map(result[i], callback);
	}

	result = false;
	jTeddy.map({length: 0}, function () {
		result = true;
	});
	ok(!result, "length: 0 plain object treated like array");

	result = false;
	jTeddy.map(document.getElementsByTagName("asdf"), function () {
		result = true;
	});
	ok(!result, "empty NodeList treated like array");

	result = jTeddy.map(Array(4), function (v, k) {
		return k % 2 ? k : [k, k, k];
	});
	equal(result.join(""), "00012223", "Array results flattened (#2616)");
});


test("jTeddy.merge()", function () {
	expect(9);

	deepEqual(
		jTeddy.merge([], []),
		[],
		"Empty arrays"
	);

	deepEqual(
		jTeddy.merge([1], [2]),
		[1, 2],
		"Basic (single-element)"
	);
	deepEqual(
		jTeddy.merge([1, 2], [3, 4]),
		[1, 2, 3, 4],
		"Basic (multiple-element)"
	);

	deepEqual(
		jTeddy.merge([1, 2], []),
		[1, 2],
		"Second empty"
	);
	deepEqual(
		jTeddy.merge([], [1, 2]),
		[1, 2],
		"First empty"
	);

	// Fixed at [5998], #3641
	deepEqual(
		jTeddy.merge([-2, -1], [0, 1, 2]),
		[-2, -1, 0, 1, 2],
		"Second array including a zero (falsy)"
	);

	// After fixing #5527
	deepEqual(
		jTeddy.merge([], [null, undefined]),
		[null, undefined],
		"Second array including null and undefined values"
	);
	deepEqual(
		jTeddy.merge({length: 0}, [1, 2]),
		{length: 2, 0: 1, 1: 2},
		"First array like"
	);
	deepEqual(
		jTeddy.merge([1, 2], {length: 1, 0: 3}),
		[1, 2, 3],
		"Second array like"
	);

});


test("jTeddy.grep()", function () {
	expect(8);

	var searchCriterion = function (value) {
		return value % 2 === 0;
	};

	deepEqual(jTeddy.grep([], searchCriterion), [], "Empty array");
	deepEqual(jTeddy.grep(new Array(4), searchCriterion), [], "Sparse array");

	deepEqual(jTeddy.grep([1, 2, 3, 4, 5, 6], searchCriterion), [2, 4, 6], "Satisfying elements present");
	deepEqual(jTeddy.grep([1, 3, 5, 7], searchCriterion), [], "Satisfying elements absent");

	deepEqual(jTeddy.grep([1, 2, 3, 4, 5, 6], searchCriterion, true), [1, 3, 5], "Satisfying elements present and grep inverted");
	deepEqual(jTeddy.grep([1, 3, 5, 7], searchCriterion, true), [1, 3, 5, 7], "Satisfying elements absent and grep inverted");

	deepEqual(jTeddy.grep([1, 2, 3, 4, 5, 6], searchCriterion, false), [2, 4, 6], "Satisfying elements present but grep explicitly uninverted");
	deepEqual(jTeddy.grep([1, 3, 5, 7], searchCriterion, false), [], "Satisfying elements absent and grep explicitly uninverted");
});

test("jTeddy.extend(Object, Object)", function () {
	expect(28);

	var empty, optionsWithLength, optionsWithDate, myKlass,
		customObject, optionsWithCustomObject, MyNumber, ret,
		nullUndef, target, recursive, obj,
		defaults, defaultsCopy, options1, options1Copy, options2, options2Copy, merged2,
		settings = {"xnumber1": 5, "xnumber2": 7, "xstring1": "peter", "xstring2": "pan"},
		options = {"xnumber2": 1, "xstring2": "x", "xxx": "newstring"},
		optionsCopy = {"xnumber2": 1, "xstring2": "x", "xxx": "newstring"},
		merged = {"xnumber1": 5, "xnumber2": 1, "xstring1": "peter", "xstring2": "x", "xxx": "newstring"},
		deep1 = {"foo": {"bar": true}},
		deep2 = {"foo": {"baz": true}, "foo2": document},
		deep2copy = {"foo": {"baz": true}, "foo2": document},
		deepmerged = {"foo": {"bar": true, "baz": true}, "foo2": document},
		arr = [1, 2, 3],
		nestedarray = {"arr": arr};

	jTeddy.extend(settings, options);
	deepEqual(settings, merged, "Check if extended: settings must be extended");
	deepEqual(options, optionsCopy, "Check if not modified: options must not be modified");

	jTeddy.extend(settings, null, options);
	deepEqual(settings, merged, "Check if extended: settings must be extended");
	deepEqual(options, optionsCopy, "Check if not modified: options must not be modified");

	jTeddy.extend(true, deep1, deep2);
	deepEqual(deep1["foo"], deepmerged["foo"], "Check if foo: settings must be extended");
	deepEqual(deep2["foo"], deep2copy["foo"], "Check if not deep2: options must not be modified");
	equal(deep1["foo2"], document, "Make sure that a deep clone was not attempted on the document");

	ok(jTeddy.extend(true, {}, nestedarray)["arr"] !== arr, "Deep extend of object must clone child array");

	// #5991
	ok(jTeddy.isArray(jTeddy.extend(true, {"arr": {}}, nestedarray)["arr"]), "Cloned array have to be an Array");
	ok(jTeddy.isPlainObject(jTeddy.extend(true, {"arr": arr}, {"arr": {}})["arr"]), "Cloned object have to be an plain object");

	empty = {};
	optionsWithLength = {"foo": {"length": -1}};
	jTeddy.extend(true, empty, optionsWithLength);
	deepEqual(empty["foo"], optionsWithLength["foo"], "The length property must copy correctly");

	empty = {};
	optionsWithDate = {"foo": {"date": new Date()}};
	jTeddy.extend(true, empty, optionsWithDate);
	deepEqual(empty["foo"], optionsWithDate["foo"], "Dates copy correctly");

	/** @constructor */
	myKlass = function () {
	};
	customObject = new myKlass();
	optionsWithCustomObject = {"foo": {"date": customObject}};
	empty = {};
	jTeddy.extend(true, empty, optionsWithCustomObject);
	ok(empty["foo"] && empty["foo"]["date"] === customObject, "Custom objects copy correctly (no methods)");

	// Makes the class a little more realistic
	myKlass.prototype = {
		"someMethod": function () {
		}
	};
	empty = {};
	jTeddy.extend(true, empty, optionsWithCustomObject);
	ok(empty["foo"] && empty["foo"]["date"] === customObject, "Custom objects copy correctly");

	MyNumber = Number;

	ret = jTeddy.extend(true, {"foo": 4}, {"foo": new MyNumber(5)});
	ok(parseInt(ret.foo, 10) === 5, "Wrapped numbers copy correctly");

	nullUndef;
	nullUndef = jTeddy.extend({}, options, {"xnumber2": null});
	ok(nullUndef["xnumber2"] === null, "Check to make sure null values are copied");

	nullUndef = jTeddy.extend({}, options, {"xnumber2": undefined});
	ok(nullUndef["xnumber2"] === options["xnumber2"], "Check to make sure undefined values are not copied");

	nullUndef = jTeddy.extend({}, options, {"xnumber0": null});
	ok(nullUndef["xnumber0"] === null, "Check to make sure null values are inserted");

	target = {};
	recursive = {foo: target, bar: 5};
	jTeddy.extend(true, target, recursive);
	deepEqual(target, {bar: 5}, "Check to make sure a recursive obj doesn't go never-ending loop by not copying it over");

	ret = jTeddy.extend(true, {foo: []}, {foo: [0]}); // 1907
	equal(ret.foo.length, 1, "Check to make sure a value with coercion 'false' copies over when necessary to fix #1907");

	ret = jTeddy.extend(true, {foo: "1,2,3"}, {foo: [1, 2, 3]});
	ok(typeof ret.foo !== "string", "Check to make sure values equal with coercion (but not actually equal) overwrite correctly");

	ret = jTeddy.extend(true, {foo: "bar"}, {foo: null});
	ok(typeof ret.foo !== "undefined", "Make sure a null value doesn't crash with deep extend, for #1908");

	obj = {foo: null};
	jTeddy.extend(true, obj, {foo: "notnull"});
	equal(obj.foo, "notnull", "Make sure a null value can be overwritten");

	function func() {
	}

	jTeddy.extend(func, {key: "value"});
	equal(func.key, "value", "Verify a function can be extended");

	defaults = {xnumber1: 5, xnumber2: 7, xstring1: "peter", xstring2: "pan"};
	defaultsCopy = {xnumber1: 5, xnumber2: 7, xstring1: "peter", xstring2: "pan"};
	options1 = {xnumber2: 1, xstring2: "x"};
	options1Copy = {xnumber2: 1, xstring2: "x"};
	options2 = {xstring2: "xx", xxx: "newstringx"};
	options2Copy = {xstring2: "xx", xxx: "newstringx"};
	merged2 = {xnumber1: 5, xnumber2: 1, xstring1: "peter", xstring2: "xx", xxx: "newstringx"};

	settings = jTeddy.extend({}, defaults, options1, options2);
	deepEqual(settings, merged2, "Check if extended: settings must be extended");
	deepEqual(defaults, defaultsCopy, "Check if not modified: options1 must not be modified");
	deepEqual(options1, options1Copy, "Check if not modified: options1 must not be modified");
	deepEqual(options2, options2Copy, "Check if not modified: options2 must not be modified");
});

test("jTeddy.extend(true,{},{a:[], o:{}}); deep copy with array, followed by object", function () {
	expect(2);

	var result, initial = {
		// This will make "copyIsArray" true
		array: [1, 2, 3, 4],
		// If "copyIsArray" doesn't get reset to false, the check
		// will evaluate true and enter the array copy block
		// instead of the object copy block. Since the ternary in the
		// "copyIsArray" block will will evaluate to false
		// (check if operating on an array with ), this will be
		// replaced by an empty array.
		object: {}
	};

	result = jTeddy.extend(true, {}, initial);

	deepEqual(result, initial, "The [result] and [initial] have equal shape and values");
	ok(!jTeddy.isArray(result.object), "result.object wasn't paved with an empty array");
});

test("jTeddy.each(Object,Function)", function () {
	expect(23);

	var i, label, seen, callback;

	seen = {};
	jTeddy.each([3, 4, 5], function (k, v) {
		seen[k] = v;
	});
	deepEqual(seen, {"0": 3, "1": 4, "2": 5}, "Array iteration");

	seen = {};
	jTeddy.each({name: "name", lang: "lang"}, function (k, v) {
		seen[k] = v;
	});
	deepEqual(seen, {name: "name", lang: "lang"}, "Object iteration");

	seen = [];
	jTeddy.each([1, 2, 3], function (k, v) {
		seen.push(v);
		if (k === 1) {
			return false;
		}
	});
	deepEqual(seen, [1, 2], "Broken array iteration");

	seen = [];
	jTeddy.each({"a": 1, "b": 2, "c": 3}, function (k, v) {
		seen.push(v);
		return false;
	});
	deepEqual(seen, [1], "Broken object iteration");

	seen = {
		Zero: function () {
		},
		One: function (a) {
			a = a;
		},
		Two: function (a, b) {
			a = a;
			b = b;
		}
	};
	callback = function (k) {
		equal(k, "foo", label + "-argument function treated like object");
	};
	for (i in seen) {
		label = i;
		seen[i].foo = "bar";
		jTeddy.each(seen[i], callback);
	}

	seen = {
		"undefined": undefined,
		"null": null,
		"false": false,
		"true": true,
		"empty string": "",
		"nonempty string": "string",
		"string \"0\"": "0",
		"negative": -1,
		"excess": 1
	};
	callback = function (k) {
		equal(k, "length", "Object with " + label + " length treated like object");
	};
	for (i in seen) {
		label = i;
		jTeddy.each({length: seen[i]}, callback);
	}

	seen = {
		"sparse Array": Array(4),
		"length: 1 plain object": {length: 1, "0": true},
		"length: 2 plain object": {length: 2, "0": true, "1": true},
		NodeList: document.getElementsByTagName("html")
	};
	callback = function (k) {
		if (seen[label]) {
			delete seen[label];
			equal(k, "0", label + " treated like array");
			return false;
		}
	};
	for (i in seen) {
		label = i;
		jTeddy.each(seen[i], callback);
	}

	seen = false;
	jTeddy.each({length: 0}, function () {
		seen = true;
	});
	ok(!seen, "length: 0 plain object treated like array");

	seen = false;
	jTeddy.each(document.getElementsByTagName("asdf"), function () {
		seen = true;
	});
	ok(!seen, "empty NodeList treated like array");

	i = 0;
	jTeddy.each(document.styleSheets, function () {
		i++;
	});
	equal(i, document.styleSheets.length, "Iteration over document.styleSheets");
});

test("jTeddy.makeArray", function () {
	expect(11);

	equal((function () {
		return jTeddy.makeArray(arguments);
	})(1, 2).join(""), "12", "Pass makeArray an arguments array");

	equal(jTeddy.makeArray([1, 2, 3]).join(""), "123", "Pass makeArray a real array");

	equal(jTeddy.makeArray().length, 0, "Pass nothing to makeArray and expect an empty array");

	equal(jTeddy.makeArray(0)[0], 0, "Pass makeArray a number");

	equal(jTeddy.makeArray("foo")[0], "foo", "Pass makeArray a string");

	equal(jTeddy.makeArray(true)[0].constructor, Boolean, "Pass makeArray a boolean");

	equal(jTeddy.makeArray(document.createElement("div"))[0].nodeName.toUpperCase(), "DIV", "Pass makeArray a single node");

	equal(jTeddy.makeArray({
		length: 2,
		0: "a",
		1: "b"
	}).join(""), "ab", "Pass makeArray an array like map (with length)");

	// function, is tricky as it has length
	equal(jTeddy.makeArray(function () {
		return 1;
	})[0](), 1, "Pass makeArray a function");

	//window, also has length
	equal(jTeddy.makeArray(window)[0], window, "Pass makeArray the window");

	equal(jTeddy.makeArray(/a/)[0].constructor, RegExp, "Pass makeArray a regex");

});

test("jTeddy.inArray", function () {
	expect(3);

	equal(jTeddy.inArray(0, false), -1, "Search in 'false' as array returns -1 and doesn't throw exception");

	equal(jTeddy.inArray(0, null), -1, "Search in 'null' as array returns -1 and doesn't throw exception");

	equal(jTeddy.inArray(0, undefined), -1, "Search in 'undefined' as array returns -1 and doesn't throw exception");
});

test("jTeddy.isEmptyObject", function () {
	expect(2);

	equal(true, jTeddy.isEmptyObject({}), "isEmptyObject on empty object literal");
	equal(false, jTeddy.isEmptyObject({a: 1}), "isEmptyObject on non-empty object literal");

	// What about this ?
	// equal(true, jTeddy.isEmptyObject(null), "isEmptyObject on null" );
});

test("jTeddy.proxy", function () {
	expect(9);

	var test2, test3, test4, fn, cb,
		test = function () {
			equal(this, thisObject, "Make sure that scope is set properly.");
		},
		thisObject = {foo: "bar", method: test};

	// Make sure normal works
	test.call(thisObject);

	// Basic scoping
	jTeddy.proxy(test, thisObject)();

	// Another take on it
	jTeddy.proxy(thisObject, "method")();

	// Make sure it doesn't freak out
	equal(jTeddy.proxy(null, thisObject), undefined, "Make sure no function was returned.");

	// Partial application
	test2 = function (a) {
		equal(a, "pre-applied", "Ensure arguments can be pre-applied.");
	};
	jTeddy.proxy(test2, null, "pre-applied")();

	// Partial application w/ normal arguments
	test3 = function (a, b) {
		equal(b, "normal", "Ensure arguments can be pre-applied and passed as usual.");
	};
	jTeddy.proxy(test3, null, "pre-applied")("normal");

	// Test old syntax
	test4 = {
		"meth": function (a) {
			equal(a, "boom", "Ensure old syntax works.");
		}
	};
	jTeddy.proxy(test4, "meth")("boom");

	// jTeddy 1.9 improved currying with `this` object
	fn = function () {
		equal(Array.prototype.join.call(arguments, ","), "arg1,arg2,arg3", "args passed");
		equal(this.foo, "bar", "this-object passed");
	};
	cb = jTeddy.proxy(fn, null, "arg1", "arg2");
	cb.call(thisObject, "arg3");
});

test("jTeddy.camelCase()", function () {

	var tests = {
		"foo-bar": "fooBar",
		"foo-bar-baz": "fooBarBaz",
		"girl-u-want": "girlUWant",
		"the-4th-dimension": "the4thDimension",
		"-o-tannenbaum": "OTannenbaum",
		"-moz-illa": "MozIlla",
		"-ms-take": "msTake"
	};

	expect(7);

	jTeddy.each(tests, function (key, val) {
		equal(jTeddy.camelCase(key), val, "Converts: " + key + " => " + val);
	});
});
