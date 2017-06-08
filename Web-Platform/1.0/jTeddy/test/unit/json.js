module("Module Json");

test("基础验证", function () {
	expect(6);

	ok(jTeddy.quoteString, "jTeddy.quoteString()");
	ok(jTeddy.toJSON, "jTeddy.toJSON()");
	ok(jTeddy.compactJSON, "jTeddy.compactJSON()");
	ok(jTeddy.evalJSON, "jTeddy.evalJSON()");
	ok(jTeddy.secureEvalJSON, "jTeddy.secureEvalJSON()");
	ok(jTeddy.parseJSON, "jTeddy.parseJSON()");
});

test("jTeddy.parseJSON", function() {
	expect( 20 );

	strictEqual( jTeddy.parseJSON( null ), null, "primitive null" );
	strictEqual( jTeddy.parseJSON("0.88"), 0.88, "Number" );
	strictEqual(
		jTeddy.parseJSON("\" \\\" \\\\ \\/ \\b \\f \\n \\r \\t \\u007E \\u263a \""),
		" \" \\ / \b \f \n \r \t ~ \u263A ",
		"String escapes"
	);
	deepEqual( jTeddy.parseJSON("{}"), {}, "Empty object" );
	deepEqual( jTeddy.parseJSON("{\"test\":1}"), { "test": 1 }, "Plain object" );
	deepEqual( jTeddy.parseJSON("[0]"), [ 0 ], "Simple array" );

	deepEqual(
		jTeddy.parseJSON("[ \"string\", -4.2, 2.7180e0, 3.14E-1, {}, [], true, false, null ]"),
		[ "string", -4.2, 2.718, 0.314, {}, [], true, false, null ],
		"Array of all data types"
	);
	deepEqual(
		jTeddy.parseJSON( "{ \"string\": \"\", \"number\": 4.2e+1, \"object\": {}," +
		"\"array\": [[]], \"boolean\": [ true, false ], \"null\": null }"),
		{ string: "", number: 42, object: {}, array: [[]], boolean: [ true, false ], "null": null },
		"Dictionary of all data types"
	);

	deepEqual( jTeddy.parseJSON("\n{\"test\":1}\t"), { "test": 1 },
		"Leading and trailing whitespace are ignored" );

	throws(function() {
		jTeddy.parseJSON();
	}, null, "Undefined raises an error" );
	throws(function() {
		jTeddy.parseJSON( "" );
	}, null, "Empty string raises an error" );
	throws(function() {
		jTeddy.parseJSON("''");
	}, null, "Single-quoted string raises an error" );
	/*

	 // Broken on IE8
	 throws(function() {
	 jTeddy.parseJSON("\" \\a \"");
	 }, null, "Invalid string escape raises an error" );

	 // Broken on IE8, Safari 5.1 Windows
	 throws(function() {
	 jTeddy.parseJSON("\"\t\"");
	 }, null, "Unescaped control character raises an error" );

	 // Broken on IE8
	 throws(function() {
	 jTeddy.parseJSON(".123");
	 }, null, "Number with no integer component raises an error" );

	 */
	throws(function() {
		var result = jTeddy.parseJSON("0101");

		// Support: IE9+
		// Ensure base-10 interpretation on browsers that erroneously accept leading-zero numbers
		if ( result === 101 ) {
			throw new Error("close enough");
		}
	}, null, "Leading-zero number raises an error or is parsed as decimal" );
	throws(function() {
		jTeddy.parseJSON("{a:1}");
	}, null, "Unquoted property raises an error" );
	throws(function() {
		jTeddy.parseJSON("{'a':1}");
	}, null, "Single-quoted property raises an error" );
	throws(function() {
		jTeddy.parseJSON("[,]");
	}, null, "Array element elision raises an error" );
	throws(function() {
		jTeddy.parseJSON("{},[]");
	}, null, "Comma expression raises an error" );
	throws(function() {
		jTeddy.parseJSON("[]\n,{}");
	}, null, "Newline-containing comma expression raises an error" );
	throws(function() {
		jTeddy.parseJSON("\"\"\n\"\"");
	}, null, "Automatic semicolon insertion raises an error" );

	strictEqual( jTeddy.parseJSON([ 0 ]), 0, "Input cast to string" );
});
