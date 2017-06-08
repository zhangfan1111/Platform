module("Module HashMap");

test("基础验证", function () {
	expect(10);

	ok($T.hashMap().arr, "$T.hashMap.arr");
	ok($T.hashMap().get, "$T.hashMap.get()");
	ok($T.hashMap().each, "$T.hashMap.each()");
	ok($T.hashMap().put, "$T.hashMap.put()");
	ok($T.hashMap().remove, "$T.hashMap.remove()");
	ok($T.hashMap().clear, "$T.hashMap.clear()");
	ok($T.hashMap().getKeys, "$T.hashMap.getKeys()");
	ok($T.hashMap().size, "$T.hashMap.size()");
	ok($T.hashMap().isEmpty, "$T.hashMap.isEmpty()");
	ok($T.hashMap().toArray, "$T.hashMap.toArray()");
});

test("arr", function () {
	expect(2);

	ok(jTeddy.hashMap().arr.push, "[].push");
	deepEqual(jTeddy.hashMap().arr, [], "the result is []")
});

test("get/put()", function () {
	expect(2);

	var map = jTeddy.hashMap();
	map.put("name", "Tom")
	deepEqual(map.get("name"), "Tom", "the key is 'name', the value is 'Tom'");
	deepEqual(map.get("names"), null, "the key is 'names', the value is null");
});

test("each()", function () {
	expect(3);

	var map = jTeddy.hashMap();
	map.put("name", "Tom");
	map.put("age", "21");
	map.put("sex", "man");

	map.each(function (key, value) {
		ok(value, "the key is " + key + " and the value is " + value);
	});
});

test("getKeys()", function () {
	expect(2);

	var map = jTeddy.hashMap();

	deepEqual(map.getKeys(), [], "the keys is empty");

	map.put("name", "Tom");
	map.put("age", "21");
	map.put("sex", "man");
	deepEqual(map.getKeys(), ["name", "age", "sex"], "the keys is ['name', 'age', 'sex']");
});

test("size()", function () {
	expect(2);

	var map = jTeddy.hashMap();
	deepEqual(map.size(), 0, "the size is 0");

	map.put("name", "Tom");
	map.put("age", "21");
	map.put("sex", "man");
	deepEqual(map.size(), 3, "the size is 3");
});

test("toArray()", function () {
	expect(2);

	var map = jTeddy.hashMap();

	deepEqual(map.toArray(), [], "the value is empty");

	map.put("name", "Tom");
	map.put("age", "21");
	map.put("sex", "man");
	deepEqual(map.toArray(), ["Tom", "21", "man"], "the value is ['Tom', '21', 'man']");
});

test("isEmpty/remove/clear()", function () {
	expect(6);

	var map = jTeddy.hashMap();

	deepEqual(map.isEmpty(), true, "the map is empty");

	map.put("name", "Tom");
	map.put("age", "21");
	map.put("sex", "man");
	deepEqual(map.isEmpty(), false, "the map is not empty");

	map.remove("name");
	deepEqual(map.size(), 2, "the size is 2");
	deepEqual(map.toArray(), ["21", "man"], "the value is ['21', 'man']");

	map.clear();
	deepEqual(map.isEmpty(), true, "the map is not empty");
	deepEqual(map.size(), 0, "the size is 0");
});
