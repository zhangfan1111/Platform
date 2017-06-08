/*!
 * jTeddy JavaScript Library v3.0.0-pre
 * http://jTeddy.com/
 *
 * Includes Sizzle.js
 * http://sizzlejs.com/
 *
 * Copyright jTeddy Foundation and other contributors
 * Released under the MIT license
 * http://jTeddy.org/license
 *
 * Date: 2015-04-20T08:05Z
 */

(function (global, factory) {

	if (typeof module === "object" && typeof module.exports === "object") {
		// For CommonJS and CommonJS-like environments where a proper `window`
		// is present, execute the factory and get jTeddy.
		// For environments that do not have a `window` with a `document`
		// (such as Node.js), expose a factory as module.exports.
		// This accentuates the need for the creation of a real `window`.
		// e.g. var jTeddy = require("jTeddy")(window);
		// See ticket #14549 for more info.
		module.exports = global.document ?
			factory(global, true) :
			function (w) {
				if (!w.document) {
					throw new Error("jTeddy requires a window with a document");
				}
				return factory(w);
			};
	} else {
		factory(global);
	}

// Pass this if window is not defined yet
}(typeof window !== "undefined" ? window : this, function (window, noGlobal) {

// Support: Firefox 18+
// Can't be in strict mode, several libs including ASP.NET trace
// the stack via arguments.caller.callee and Firefox dies if
// you try to trace through "use strict" call chains. (#13335)
//
var arr = [];

var concat = arr.concat;

var indexOf = arr.indexOf;

var push = arr.push;

var slice = arr.slice;

var class2type = {};

var toString = class2type.toString;

var hasOwn = class2type.hasOwnProperty;

var support = {};

var document = window.document;


	//var version = "3.0.0-pre",
	var version = "V1.0",

	//定义jTeddy
		jTeddy = function (selector, context) {
			// HANDLE: $(""), $(null), $(undefined), $(false)
			if (!selector) {
				return jTeddy;
			}

			return selector;

			//	return new jTeddy.fn.init(selector, context);
		},

	// Support: Android<4.1
	// Make sure we trim BOM and NBSP
		rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g,

	// Matches dashed string for camelizing
		rmsPrefix = /^-ms-/,
		rdashAlpha = /-([\da-z])/gi,

	// Used by jTeddy.camelCase as callback to replace()
		fcamelCase = function (all, letter) {
			return letter.toUpperCase();
		};

	jTeddy.fn = jTeddy.prototype = {
		//当前jTeddy的版本
		jteddy: version,

		constructor: jTeddy,

		//jTeddy对象的默认长度为0
		length: 0
	};

	jTeddy.extend = jTeddy.fn.extend = function () {
		var options, name, src, copy, copyIsArray, clone,
			target = arguments[0] || {},
			i = 1,
			length = arguments.length,
			deep = false;

		// Handle a deep copy situation
		if (typeof target === "boolean") {
			deep = target;

			// Skip the boolean and the target
			target = arguments[i] || {};
			i++;
		}

		// Handle case when target is a string or something (possible in deep copy)
		if (typeof target !== "object" && !jTeddy.isFunction(target)) {
			target = {};
		}

		// Extend jTeddy itself if only one argument is passed
		if (i === length) {
			target = this;
			i--;
		}

		for (; i < length; i++) {
			// Only deal with non-null/undefined values
			if ((options = arguments[i]) != null) {
				// Extend the base object
				for (name in options) {
					src = target[name];
					copy = options[name];

					// Prevent never-ending loop
					if (target === copy) {
						continue;
					}

					// Recurse if we're merging plain objects or arrays
					if (deep && copy && ( jTeddy.isPlainObject(copy) ||
						(copyIsArray = jTeddy.isArray(copy)) )) {

						if (copyIsArray) {
							copyIsArray = false;
							clone = src && jTeddy.isArray(src) ? src : [];

						} else {
							clone = src && jTeddy.isPlainObject(src) ? src : {};
						}

						// Never move original objects, clone them
						target[name] = jTeddy.extend(deep, clone, copy);

						// Don't bring in undefined values
					} else if (copy !== undefined) {
						target[name] = copy;
					}
				}
			}
		}

		// Return the modified object
		return target;
	};

	jTeddy.extend({
		// Unique for each copy of jTeddy on the page
		expando: "jTeddy" + ( version + Math.random() ).replace(/\D/g, ""),

		// Assume jTeddy is ready without the ready module
		isReady: true,

		error: function (msg) {
			throw new Error(msg);
		},

		noop: function () {
		},

		isFunction: function (obj) {
			return jTeddy.type(obj) === "function";
		},

		isArray: Array.isArray || function (obj) {
			return jTeddy.type(obj) === "array";
		},

		isWindow: function (obj) {
			return obj != null && obj === obj.window;
		},

		isNumeric: function (obj) {
			return jTeddy.type(obj) !== "array" && !isNaN(parseFloat(obj)) && isFinite(obj);
		},

		isPlainObject: function (obj) {
			// Not plain objects:
			// - Any object or value whose internal [[Class]] property is not "[object Object]"
			// - DOM nodes
			// - window
			if (jTeddy.type(obj) !== "object" || obj.nodeType || jTeddy.isWindow(obj)) {
				return false;
			}

			if (obj.constructor && !hasOwn.call(obj.constructor.prototype, "isPrototypeOf")) {
				return false;
			}

			// If the function hasn't returned already, we're confident that
			// |obj| is a plain object, created by {} or constructed with new Object
			return true;
		},

		isEmptyObject: function (obj) {
			var name;
			for (name in obj) {
				return false;
			}
			return true;
		},

		type: function (obj) {
			if (obj == null) {
				return obj + "";
			}
			// Support: Android<4.0 (functionish RegExp)
			return typeof obj === "object" || typeof obj === "function" ?
			class2type[toString.call(obj)] || "object" :
				typeof obj;
		},

		// Evaluates a script in a global context
		globalEval: function (code) {
			var script = document.createElement("script");

			script.text = code;
			document.head.appendChild(script).parentNode.removeChild(script);
		},

		// Convert dashed to camelCase; used by the css and data modules
		// Support: IE9-11+
		// Microsoft forgot to hump their vendor prefix (#9572)
		camelCase: function (string) {
			return string.replace(rmsPrefix, "ms-").replace(rdashAlpha, fcamelCase);
		},

		nodeName: function (elem, name) {
			return elem.nodeName && elem.nodeName.toLowerCase() === name.toLowerCase();
		},

		each: function (obj, callback) {
			var i = 0,
				length = obj.length,
				isArray = isArraylike(obj);

			if (isArray) {
				for (; i < length; i++) {
					if (callback.call(obj[i], i, obj[i]) === false) {
						break;
					}
				}
			} else {
				for (i in obj) {
					if (callback.call(obj[i], i, obj[i]) === false) {
						break;
					}
				}
			}

			return obj;
		},

		// Support: Android<4.1
		trim: function (text) {
			return text == null ?
				"" :
				( text + "" ).replace(rtrim, "");
		},

		// results is for internal usage only
		makeArray: function (arr, results) {
			var ret = results || [];

			if (arr != null) {
				if (isArraylike(Object(arr))) {
					jTeddy.merge(ret,
						typeof arr === "string" ?
							[arr] : arr
					);
				} else {
					push.call(ret, arr);
				}
			}

			return ret;
		},

		inArray: function (elem, arr, i) {
			var len;

			if (arr) {
				if (indexOf) {
					return indexOf.call(arr, elem, i);
				}

				len = arr.length;
				i = i ? i < 0 ? Math.max(0, len + i) : i : 0;

				for (; i < len; i++) {
					// Skip accessing in sparse arrays
					if (i in arr && arr[i] === elem) {
						return i;
					}
				}
			}

			return -1;
		},

		// Support: Android<4.1, PhantomJS<2
		// push.apply(_, arraylike) throws on ancient WebKit
		merge: function (first, second) {
			var len = +second.length,
				j = 0,
				i = first.length;

			for (; j < len; j++) {
				first[i++] = second[j];
			}

			first.length = i;

			return first;
		},

		grep: function (elems, callback, invert) {
			var callbackInverse,
				matches = [],
				i = 0,
				length = elems.length,
				callbackExpect = !invert;

			// Go through the array, only saving the items
			// that pass the validator function
			for (; i < length; i++) {
				callbackInverse = !callback(elems[i], i);
				if (callbackInverse !== callbackExpect) {
					matches.push(elems[i]);
				}
			}

			return matches;
		},

		// arg is for internal usage only
		map: function (elems, callback, arg) {
			var value,
				i = 0,
				length = elems.length,
				isArray = isArraylike(elems),
				ret = [];

			// Go through the array, translating each of the items to their new values
			if (isArray) {
				for (; i < length; i++) {
					value = callback(elems[i], i, arg);

					if (value != null) {
						ret.push(value);
					}
				}

				// Go through every key on the object,
			} else {
				for (i in elems) {
					value = callback(elems[i], i, arg);

					if (value != null) {
						ret.push(value);
					}
				}
			}

			// Flatten any nested arrays
			return concat.apply([], ret);
		},

		// A global GUID counter for objects
		guid: 1,

		// Bind a function to a context, optionally partially applying any
		// arguments.
		proxy: function (fn, context) {
			var tmp, args, proxy;

			if (typeof context === "string") {
				tmp = fn[context];
				context = fn;
				fn = tmp;
			}

			// Quick check to determine if target is callable, in the spec
			// this throws a TypeError, but we will just return undefined.
			if (!jTeddy.isFunction(fn)) {
				return undefined;
			}

			// Simulated bind
			args = slice.call(arguments, 2);
			proxy = function () {
				return fn.apply(context || this, args.concat(slice.call(arguments)));
			};

			// Set the guid of unique handler to the same of original handler, so it can be removed
			proxy.guid = fn.guid = fn.guid || jTeddy.guid++;

			return proxy;
		},

		now: Date.now,

		// jTeddy.support is not used in Core but other projects attach their
		// properties to it so it needs to exist.
		support: support
	});

	// Populate the class2type map
	jTeddy.each("Boolean Number String Function Array Date RegExp Object Error".split(" "),
		function (i, name) {
			class2type["[object " + name + "]"] = name.toLowerCase();
		}
	);

	function isArraylike(obj) {
		var length = obj.length,
			type = jTeddy.type(obj);

		if (type === "function" || jTeddy.isWindow(obj)) {
			return false;
		}

		if (obj.nodeType === 1 && length) {
			return true;
		}

		return type === "array" || length === 0 ||
			typeof length === "number" && length > 0 && ( length - 1 ) in obj;
	}
var Sizzle =
/*!
 * Sizzle CSS Selector Engine v2.1.1
 * http://sizzlejs.com/
 *
 * Copyright 2008, 2014 jQuery Foundation, Inc. and other contributors
 * Released under the MIT license
 * http://jquery.org/license
 *
 * Date: 2014-12-15
 */
(function( window ) {

var i,
	support,
	Expr,
	getText,
	isXML,
	tokenize,
	compile,
	select,
	outermostContext,
	sortInput,
	hasDuplicate,

	// Local document vars
	setDocument,
	document,
	docElem,
	documentIsHTML,
	rbuggyQSA,
	rbuggyMatches,
	matches,
	contains,

	// Instance-specific data
	expando = "sizzle" + 1 * new Date(),
	preferredDoc = window.document,
	dirruns = 0,
	done = 0,
	classCache = createCache(),
	tokenCache = createCache(),
	compilerCache = createCache(),
	sortOrder = function( a, b ) {
		if ( a === b ) {
			hasDuplicate = true;
		}
		return 0;
	},

	// General-purpose constants
	MAX_NEGATIVE = 1 << 31,

	// Instance methods
	hasOwn = ({}).hasOwnProperty,
	arr = [],
	pop = arr.pop,
	push_native = arr.push,
	push = arr.push,
	slice = arr.slice,
	// Use a stripped-down indexOf as it's faster than native
	// http://jsperf.com/thor-indexof-vs-for/5
	indexOf = function( list, elem ) {
		var i = 0,
			len = list.length;
		for ( ; i < len; i++ ) {
			if ( list[i] === elem ) {
				return i;
			}
		}
		return -1;
	},

	booleans = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped",

	// Regular expressions

	// http://www.w3.org/TR/css3-selectors/#whitespace
	whitespace = "[\\x20\\t\\r\\n\\f]",

	// http://www.w3.org/TR/CSS21/syndata.html#value-def-identifier
	identifier = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+",

	// Attribute selectors: http://www.w3.org/TR/selectors/#attribute-selectors
	attributes = "\\[" + whitespace + "*(" + identifier + ")(?:" + whitespace +
		// Operator (capture 2)
		"*([*^$|!~]?=)" + whitespace +
		// "Attribute values must be CSS identifiers [capture 5] or strings [capture 3 or capture 4]"
		"*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + identifier + "))|)" + whitespace +
		"*\\]",

	pseudos = ":(" + identifier + ")(?:\\((" +
		// To reduce the number of selectors needing tokenize in the preFilter, prefer arguments:
		// 1. quoted (capture 3; capture 4 or capture 5)
		"('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|" +
		// 2. simple (capture 6)
		"((?:\\\\.|[^\\\\()[\\]]|" + attributes + ")*)|" +
		// 3. anything else (capture 2)
		".*" +
		")\\)|)",

	// Leading and non-escaped trailing whitespace, capturing some non-whitespace characters preceding the latter
	rwhitespace = new RegExp( whitespace + "+", "g" ),
	rtrim = new RegExp( "^" + whitespace + "+|((?:^|[^\\\\])(?:\\\\.)*)" + whitespace + "+$", "g" ),

	rcomma = new RegExp( "^" + whitespace + "*," + whitespace + "*" ),
	rcombinators = new RegExp( "^" + whitespace + "*([>+~]|" + whitespace + ")" + whitespace + "*" ),

	rattributeQuotes = new RegExp( "=" + whitespace + "*([^\\]'\"]*?)" + whitespace + "*\\]", "g" ),

	rpseudo = new RegExp( pseudos ),
	ridentifier = new RegExp( "^" + identifier + "$" ),

	matchExpr = {
		"ID": new RegExp( "^#(" + identifier + ")" ),
		"CLASS": new RegExp( "^\\.(" + identifier + ")" ),
		"TAG": new RegExp( "^(" + identifier + "|[*])" ),
		"ATTR": new RegExp( "^" + attributes ),
		"PSEUDO": new RegExp( "^" + pseudos ),
		"CHILD": new RegExp( "^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + whitespace +
			"*(even|odd|(([+-]|)(\\d*)n|)" + whitespace + "*(?:([+-]|)" + whitespace +
			"*(\\d+)|))" + whitespace + "*\\)|)", "i" ),
		"bool": new RegExp( "^(?:" + booleans + ")$", "i" ),
		// For use in libraries implementing .is()
		// We use this for POS matching in `select`
		"needsContext": new RegExp( "^" + whitespace + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" +
			whitespace + "*((?:-\\d)?\\d*)" + whitespace + "*\\)|)(?=[^-]|$)", "i" )
	},

	rinputs = /^(?:input|select|textarea|button)$/i,
	rheader = /^h\d$/i,

	rnative = /^[^{]+\{\s*\[native \w/,

	// Easily-parseable/retrievable ID or TAG or CLASS selectors
	rquickExpr = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/,

	rsibling = /[+~]/,
	rescape = /'|\\/g,

	// CSS escapes http://www.w3.org/TR/CSS21/syndata.html#escaped-characters
	runescape = new RegExp( "\\\\([\\da-f]{1,6}" + whitespace + "?|(" + whitespace + ")|.)", "ig" ),
	funescape = function( _, escaped, escapedWhitespace ) {
		var high = "0x" + escaped - 0x10000;
		// NaN means non-codepoint
		// Support: Firefox<24
		// Workaround erroneous numeric interpretation of +"0x"
		return high !== high || escapedWhitespace ?
			escaped :
			high < 0 ?
				// BMP codepoint
				String.fromCharCode( high + 0x10000 ) :
				// Supplemental Plane codepoint (surrogate pair)
				String.fromCharCode( high >> 10 | 0xD800, high & 0x3FF | 0xDC00 );
	},

	// Used for iframes
	// See setDocument()
	// Removing the function wrapper causes a "Permission Denied"
	// error in IE
	unloadHandler = function() {
		setDocument();
	};

// Optimize for push.apply( _, NodeList )
try {
	push.apply(
		(arr = slice.call( preferredDoc.childNodes )),
		preferredDoc.childNodes
	);
	// Support: Android<4.0
	// Detect silently failing push.apply
	arr[ preferredDoc.childNodes.length ].nodeType;
} catch ( e ) {
	push = { apply: arr.length ?

		// Leverage slice if possible
		function( target, els ) {
			push_native.apply( target, slice.call(els) );
		} :

		// Support: IE<9
		// Otherwise append directly
		function( target, els ) {
			var j = target.length,
				i = 0;
			// Can't trust NodeList.length
			while ( (target[j++] = els[i++]) ) {}
			target.length = j - 1;
		}
	};
}

function Sizzle( selector, context, results, seed ) {
	var match, elem, m, nodeType,
		// QSA vars
		i, groups, old, nid, newContext, newSelector;

	if ( ( context ? context.ownerDocument || context : preferredDoc ) !== document ) {
		setDocument( context );
	}

	context = context || document;
	results = results || [];

	if ( !selector || typeof selector !== "string" ) {
		return results;
	}

	if ( (nodeType = context.nodeType) !== 1 && nodeType !== 9 && nodeType !== 11 ) {
		return [];
	}

	if ( documentIsHTML && !seed ) {

		// Try to shortcut find operations when possible (e.g., not under DocumentFragment)
		if ( nodeType !== 11 && (match = rquickExpr.exec( selector )) ) {
			// Speed-up: Sizzle("#ID")
			if ( (m = match[1]) ) {
				if ( nodeType === 9 ) {
					elem = context.getElementById( m );
					// Check parentNode to catch when Blackberry 4.6 returns
					// nodes that are no longer in the document (jQuery #6963)
					if ( elem && elem.parentNode ) {
						// Handle the case where IE, Opera, and Webkit return items
						// by name instead of ID
						if ( elem.id === m ) {
							results.push( elem );
							return results;
						}
					} else {
						return results;
					}
				} else {
					// Context is not a document
					if ( context.ownerDocument && (elem = context.ownerDocument.getElementById( m )) &&
						contains( context, elem ) && elem.id === m ) {
						results.push( elem );
						return results;
					}
				}

			// Speed-up: Sizzle("TAG")
			} else if ( match[2] ) {
				push.apply( results, context.getElementsByTagName( selector ) );
				return results;

			// Speed-up: Sizzle(".CLASS")
			} else if ( (m = match[3]) && support.getElementsByClassName ) {
				push.apply( results, context.getElementsByClassName( m ) );
				return results;
			}
		}

		// QSA path
		if ( support.qsa && (!rbuggyQSA || !rbuggyQSA.test( selector )) ) {
			nid = old = expando;
			newContext = context;
			newSelector = nodeType !== 1 && selector;

			// qSA works strangely on Element-rooted queries
			// We can work around this by specifying an extra ID on the root
			// and working up from there (Thanks to Andrew Dupont for the technique)
			// IE 8 doesn't work on object elements
			if ( nodeType === 1 && context.nodeName.toLowerCase() !== "object" ) {
				groups = tokenize( selector );

				if ( (old = context.getAttribute("id")) ) {
					nid = old.replace( rescape, "\\$&" );
				} else {
					context.setAttribute( "id", nid );
				}
				nid = "[id='" + nid + "'] ";

				i = groups.length;
				while ( i-- ) {
					groups[i] = nid + toSelector( groups[i] );
				}
				newContext = rsibling.test( selector ) && testContext( context.parentNode ) || context;
				newSelector = groups.join(",");
			}

			if ( newSelector ) {
				try {
					push.apply( results,
						newContext.querySelectorAll( newSelector )
					);
					return results;
				} catch(qsaError) {
				} finally {
					if ( !old ) {
						context.removeAttribute("id");
					}
				}
			}
		}
	}

	// All others
	return select( selector.replace( rtrim, "$1" ), context, results, seed );
}

/**
 * Create key-value caches of limited size
 * @returns {Function(string, Object)} Returns the Object data after storing it on itself with
 *	property name the (space-suffixed) string and (if the cache is larger than Expr.cacheLength)
 *	deleting the oldest entry
 */
function createCache() {
	var keys = [];

	function cache( key, value ) {
		// Use (key + " ") to avoid collision with native prototype properties (see Issue #157)
		if ( keys.push( key + " " ) > Expr.cacheLength ) {
			// Only keep the most recent entries
			delete cache[ keys.shift() ];
		}
		return (cache[ key + " " ] = value);
	}
	return cache;
}

/**
 * Mark a function for special use by Sizzle
 * @param {Function} fn The function to mark
 */
function markFunction( fn ) {
	fn[ expando ] = true;
	return fn;
}

/**
 * Support testing using an element
 * @param {Function} fn Passed the created div and expects a boolean result
 */
function assert( fn ) {
	var div = document.createElement("div");

	try {
		return !!fn( div );
	} catch (e) {
		return false;
	} finally {
		// Remove from its parent by default
		if ( div.parentNode ) {
			div.parentNode.removeChild( div );
		}
		// release memory in IE
		div = null;
	}
}

/**
 * Adds the same handler for all of the specified attrs
 * @param {String} attrs Pipe-separated list of attributes
 * @param {Function} handler The method that will be applied
 */
function addHandle( attrs, handler ) {
	var arr = attrs.split("|"),
		i = attrs.length;

	while ( i-- ) {
		Expr.attrHandle[ arr[i] ] = handler;
	}
}

/**
 * Checks document order of two siblings
 * @param {Element} a
 * @param {Element} b
 * @returns {Number} Returns less than 0 if a precedes b, greater than 0 if a follows b
 */
function siblingCheck( a, b ) {
	var cur = b && a,
		diff = cur && a.nodeType === 1 && b.nodeType === 1 &&
			( ~b.sourceIndex || MAX_NEGATIVE ) -
			( ~a.sourceIndex || MAX_NEGATIVE );

	// Use IE sourceIndex if available on both nodes
	if ( diff ) {
		return diff;
	}

	// Check if b follows a
	if ( cur ) {
		while ( (cur = cur.nextSibling) ) {
			if ( cur === b ) {
				return -1;
			}
		}
	}

	return a ? 1 : -1;
}

/**
 * Returns a function to use in pseudos for input types
 * @param {String} type
 */
function createInputPseudo( type ) {
	return function( elem ) {
		var name = elem.nodeName.toLowerCase();
		return name === "input" && elem.type === type;
	};
}

/**
 * Returns a function to use in pseudos for buttons
 * @param {String} type
 */
function createButtonPseudo( type ) {
	return function( elem ) {
		var name = elem.nodeName.toLowerCase();
		return (name === "input" || name === "button") && elem.type === type;
	};
}

/**
 * Returns a function to use in pseudos for positionals
 * @param {Function} fn
 */
function createPositionalPseudo( fn ) {
	return markFunction(function( argument ) {
		argument = +argument;
		return markFunction(function( seed, matches ) {
			var j,
				matchIndexes = fn( [], seed.length, argument ),
				i = matchIndexes.length;

			// Match elements found at the specified indexes
			while ( i-- ) {
				if ( seed[ (j = matchIndexes[i]) ] ) {
					seed[j] = !(matches[j] = seed[j]);
				}
			}
		});
	});
}

/**
 * Checks a node for validity as a Sizzle context
 * @param {Element|Object=} context
 * @returns {Element|Object|Boolean} The input node if acceptable, otherwise a falsy value
 */
function testContext( context ) {
	return context && typeof context.getElementsByTagName !== "undefined" && context;
}

// Expose support vars for convenience
support = Sizzle.support = {};

/**
 * Detects XML nodes
 * @param {Element|Object} elem An element or a document
 * @returns {Boolean} True iff elem is a non-HTML XML node
 */
isXML = Sizzle.isXML = function( elem ) {
	// documentElement is verified for cases where it doesn't yet exist
	// (such as loading iframes in IE - #4833)
	var documentElement = elem && (elem.ownerDocument || elem).documentElement;
	return documentElement ? documentElement.nodeName !== "HTML" : false;
};

/**
 * Sets document-related variables once based on the current document
 * @param {Element|Object} [doc] An element or document object to use to set the document
 * @returns {Object} Returns the current document
 */
setDocument = Sizzle.setDocument = function( node ) {
	var hasCompare, parent,
		doc = node ? node.ownerDocument || node : preferredDoc;

	// If no document and documentElement is available, return
	if ( doc === document || doc.nodeType !== 9 || !doc.documentElement ) {
		return document;
	}

	// Set our document
	document = doc;
	docElem = doc.documentElement;
	parent = doc.defaultView;

	// Support: IE>8
	// If iframe document is assigned to "document" variable and if iframe has been reloaded,
	// IE will throw "permission denied" error when accessing "document" variable, see jQuery #13936
	// IE6-8 do not support the defaultView property so parent will be undefined
	if ( parent && parent !== parent.top ) {
		// IE11 does not have attachEvent, so all must suffer
		if ( parent.addEventListener ) {
			parent.addEventListener( "unload", unloadHandler, false );
		} else if ( parent.attachEvent ) {
			parent.attachEvent( "onunload", unloadHandler );
		}
	}

	/* Support tests
	---------------------------------------------------------------------- */
	documentIsHTML = !isXML( doc );

	/* Attributes
	---------------------------------------------------------------------- */

	// Support: IE<8
	// Verify that getAttribute really returns attributes and not properties
	// (excepting IE8 booleans)
	support.attributes = assert(function( div ) {
		div.className = "i";
		return !div.getAttribute("className");
	});

	/* getElement(s)By*
	---------------------------------------------------------------------- */

	// Check if getElementsByTagName("*") returns only elements
	support.getElementsByTagName = assert(function( div ) {
		div.appendChild( doc.createComment("") );
		return !div.getElementsByTagName("*").length;
	});

	// Support: IE<9
	support.getElementsByClassName = rnative.test( doc.getElementsByClassName );

	// Support: IE<10
	// Check if getElementById returns elements by name
	// The broken getElementById methods don't pick up programatically-set names,
	// so use a roundabout getElementsByName test
	support.getById = assert(function( div ) {
		docElem.appendChild( div ).id = expando;
		return !doc.getElementsByName || !doc.getElementsByName( expando ).length;
	});

	// ID find and filter
	if ( support.getById ) {
		Expr.find["ID"] = function( id, context ) {
			if ( typeof context.getElementById !== "undefined" && documentIsHTML ) {
				var m = context.getElementById( id );
				// Check parentNode to catch when Blackberry 4.6 returns
				// nodes that are no longer in the document #6963
				return m && m.parentNode ? [ m ] : [];
			}
		};
		Expr.filter["ID"] = function( id ) {
			var attrId = id.replace( runescape, funescape );
			return function( elem ) {
				return elem.getAttribute("id") === attrId;
			};
		};
	} else {
		// Support: IE6/7
		// getElementById is not reliable as a find shortcut
		delete Expr.find["ID"];

		Expr.filter["ID"] =  function( id ) {
			var attrId = id.replace( runescape, funescape );
			return function( elem ) {
				var node = typeof elem.getAttributeNode !== "undefined" && elem.getAttributeNode("id");
				return node && node.value === attrId;
			};
		};
	}

	// Tag
	Expr.find["TAG"] = support.getElementsByTagName ?
		function( tag, context ) {
			if ( typeof context.getElementsByTagName !== "undefined" ) {
				return context.getElementsByTagName( tag );

			// DocumentFragment nodes don't have gEBTN
			} else if ( support.qsa ) {
				return context.querySelectorAll( tag );
			}
		} :

		function( tag, context ) {
			var elem,
				tmp = [],
				i = 0,
				// By happy coincidence, a (broken) gEBTN appears on DocumentFragment nodes too
				results = context.getElementsByTagName( tag );

			// Filter out possible comments
			if ( tag === "*" ) {
				while ( (elem = results[i++]) ) {
					if ( elem.nodeType === 1 ) {
						tmp.push( elem );
					}
				}

				return tmp;
			}
			return results;
		};

	// Class
	Expr.find["CLASS"] = support.getElementsByClassName && function( className, context ) {
		if ( documentIsHTML ) {
			return context.getElementsByClassName( className );
		}
	};

	/* QSA/matchesSelector
	---------------------------------------------------------------------- */

	// QSA and matchesSelector support

	// matchesSelector(:active) reports false when true (IE9/Opera 11.5)
	rbuggyMatches = [];

	// qSa(:focus) reports false when true (Chrome 21)
	// We allow this because of a bug in IE8/9 that throws an error
	// whenever `document.activeElement` is accessed on an iframe
	// So, we allow :focus to pass through QSA all the time to avoid the IE error
	// See http://bugs.jquery.com/ticket/13378
	rbuggyQSA = [];

	if ( (support.qsa = rnative.test( doc.querySelectorAll )) ) {
		// Build QSA regex
		// Regex strategy adopted from Diego Perini
		assert(function( div ) {
			// Select is set to empty string on purpose
			// This is to test IE's treatment of not explicitly
			// setting a boolean content attribute,
			// since its presence should be enough
			// http://bugs.jquery.com/ticket/12359
			docElem.appendChild( div ).innerHTML = "<a id='" + expando + "'></a>" +
				"<select id='" + expando + "-\f]' msallowcapture=''>" +
				"<option selected=''></option></select>";

			// Support: IE8, Opera 11-12.16
			// Nothing should be selected when empty strings follow ^= or $= or *=
			// The test attribute must be unknown in Opera but "safe" for WinRT
			// http://msdn.microsoft.com/en-us/library/ie/hh465388.aspx#attribute_section
			if ( div.querySelectorAll("[msallowcapture^='']").length ) {
				rbuggyQSA.push( "[*^$]=" + whitespace + "*(?:''|\"\")" );
			}

			// Support: IE8
			// Boolean attributes and "value" are not treated correctly
			if ( !div.querySelectorAll("[selected]").length ) {
				rbuggyQSA.push( "\\[" + whitespace + "*(?:value|" + booleans + ")" );
			}

			// Support: Chrome<29, Android<4.2+, Safari<7.0+, iOS<7.0+, PhantomJS<1.9.7+
			if ( !div.querySelectorAll( "[id~=" + expando + "-]" ).length ) {
				rbuggyQSA.push("~=");
			}

			// Webkit/Opera - :checked should return selected option elements
			// http://www.w3.org/TR/2011/REC-css3-selectors-20110929/#checked
			// IE8 throws error here and will not see later tests
			if ( !div.querySelectorAll(":checked").length ) {
				rbuggyQSA.push(":checked");
			}

			// Support: Safari 8+, iOS 8+
			// https://bugs.webkit.org/show_bug.cgi?id=136851
			// In-page `selector#id sibing-combinator selector` fails
			if ( !div.querySelectorAll( "a#" + expando + "+*" ).length ) {
				rbuggyQSA.push(".#.+[+~]");
			}
		});

		assert(function( div ) {
			// Support: Windows 8 Native Apps
			// The type and name attributes are restricted during .innerHTML assignment
			var input = doc.createElement("input");
			input.setAttribute( "type", "hidden" );
			div.appendChild( input ).setAttribute( "name", "D" );

			// Support: IE8
			// Enforce case-sensitivity of name attribute
			if ( div.querySelectorAll("[name=d]").length ) {
				rbuggyQSA.push( "name" + whitespace + "*[*^$|!~]?=" );
			}

			// FF 3.5 - :enabled/:disabled and hidden elements (hidden elements are still enabled)
			// IE8 throws error here and will not see later tests
			if ( !div.querySelectorAll(":enabled").length ) {
				rbuggyQSA.push( ":enabled", ":disabled" );
			}

			// Opera 10-11 does not throw on post-comma invalid pseudos
			div.querySelectorAll("*,:x");
			rbuggyQSA.push(",.*:");
		});
	}

	if ( (support.matchesSelector = rnative.test( (matches = docElem.matches ||
		docElem.webkitMatchesSelector ||
		docElem.mozMatchesSelector ||
		docElem.oMatchesSelector ||
		docElem.msMatchesSelector) )) ) {

		assert(function( div ) {
			// Check to see if it's possible to do matchesSelector
			// on a disconnected node (IE 9)
			support.disconnectedMatch = matches.call( div, "div" );

			// This should fail with an exception
			// Gecko does not error, returns false instead
			matches.call( div, "[s!='']:x" );
			rbuggyMatches.push( "!=", pseudos );
		});
	}

	rbuggyQSA = rbuggyQSA.length && new RegExp( rbuggyQSA.join("|") );
	rbuggyMatches = rbuggyMatches.length && new RegExp( rbuggyMatches.join("|") );

	/* Contains
	---------------------------------------------------------------------- */
	hasCompare = rnative.test( docElem.compareDocumentPosition );

	// Element contains another
	// Purposefully does not implement inclusive descendent
	// As in, an element does not contain itself
	contains = hasCompare || rnative.test( docElem.contains ) ?
		function( a, b ) {
			var adown = a.nodeType === 9 ? a.documentElement : a,
				bup = b && b.parentNode;
			return a === bup || !!( bup && bup.nodeType === 1 && (
				adown.contains ?
					adown.contains( bup ) :
					a.compareDocumentPosition && a.compareDocumentPosition( bup ) & 16
			));
		} :
		function( a, b ) {
			if ( b ) {
				while ( (b = b.parentNode) ) {
					if ( b === a ) {
						return true;
					}
				}
			}
			return false;
		};

	/* Sorting
	---------------------------------------------------------------------- */

	// Document order sorting
	sortOrder = hasCompare ?
	function( a, b ) {

		// Flag for duplicate removal
		if ( a === b ) {
			hasDuplicate = true;
			return 0;
		}

		// Sort on method existence if only one input has compareDocumentPosition
		var compare = !a.compareDocumentPosition - !b.compareDocumentPosition;
		if ( compare ) {
			return compare;
		}

		// Calculate position if both inputs belong to the same document
		compare = ( a.ownerDocument || a ) === ( b.ownerDocument || b ) ?
			a.compareDocumentPosition( b ) :

			// Otherwise we know they are disconnected
			1;

		// Disconnected nodes
		if ( compare & 1 ||
			(!support.sortDetached && b.compareDocumentPosition( a ) === compare) ) {

			// Choose the first element that is related to our preferred document
			if ( a === doc || a.ownerDocument === preferredDoc && contains(preferredDoc, a) ) {
				return -1;
			}
			if ( b === doc || b.ownerDocument === preferredDoc && contains(preferredDoc, b) ) {
				return 1;
			}

			// Maintain original order
			return sortInput ?
				( indexOf( sortInput, a ) - indexOf( sortInput, b ) ) :
				0;
		}

		return compare & 4 ? -1 : 1;
	} :
	function( a, b ) {
		// Exit early if the nodes are identical
		if ( a === b ) {
			hasDuplicate = true;
			return 0;
		}

		var cur,
			i = 0,
			aup = a.parentNode,
			bup = b.parentNode,
			ap = [ a ],
			bp = [ b ];

		// Parentless nodes are either documents or disconnected
		if ( !aup || !bup ) {
			return a === doc ? -1 :
				b === doc ? 1 :
				aup ? -1 :
				bup ? 1 :
				sortInput ?
				( indexOf( sortInput, a ) - indexOf( sortInput, b ) ) :
				0;

		// If the nodes are siblings, we can do a quick check
		} else if ( aup === bup ) {
			return siblingCheck( a, b );
		}

		// Otherwise we need full lists of their ancestors for comparison
		cur = a;
		while ( (cur = cur.parentNode) ) {
			ap.unshift( cur );
		}
		cur = b;
		while ( (cur = cur.parentNode) ) {
			bp.unshift( cur );
		}

		// Walk down the tree looking for a discrepancy
		while ( ap[i] === bp[i] ) {
			i++;
		}

		return i ?
			// Do a sibling check if the nodes have a common ancestor
			siblingCheck( ap[i], bp[i] ) :

			// Otherwise nodes in our document sort first
			ap[i] === preferredDoc ? -1 :
			bp[i] === preferredDoc ? 1 :
			0;
	};

	return doc;
};

Sizzle.matches = function( expr, elements ) {
	return Sizzle( expr, null, null, elements );
};

Sizzle.matchesSelector = function( elem, expr ) {
	// Set document vars if needed
	if ( ( elem.ownerDocument || elem ) !== document ) {
		setDocument( elem );
	}

	// Make sure that attribute selectors are quoted
	expr = expr.replace( rattributeQuotes, "='$1']" );

	if ( support.matchesSelector && documentIsHTML &&
		( !rbuggyMatches || !rbuggyMatches.test( expr ) ) &&
		( !rbuggyQSA     || !rbuggyQSA.test( expr ) ) ) {

		try {
			var ret = matches.call( elem, expr );

			// IE 9's matchesSelector returns false on disconnected nodes
			if ( ret || support.disconnectedMatch ||
					// As well, disconnected nodes are said to be in a document
					// fragment in IE 9
					elem.document && elem.document.nodeType !== 11 ) {
				return ret;
			}
		} catch (e) {}
	}

	return Sizzle( expr, document, null, [ elem ] ).length > 0;
};

Sizzle.contains = function( context, elem ) {
	// Set document vars if needed
	if ( ( context.ownerDocument || context ) !== document ) {
		setDocument( context );
	}
	return contains( context, elem );
};

Sizzle.attr = function( elem, name ) {
	// Set document vars if needed
	if ( ( elem.ownerDocument || elem ) !== document ) {
		setDocument( elem );
	}

	var fn = Expr.attrHandle[ name.toLowerCase() ],
		// Don't get fooled by Object.prototype properties (jQuery #13807)
		val = fn && hasOwn.call( Expr.attrHandle, name.toLowerCase() ) ?
			fn( elem, name, !documentIsHTML ) :
			undefined;

	return val !== undefined ?
		val :
		support.attributes || !documentIsHTML ?
			elem.getAttribute( name ) :
			(val = elem.getAttributeNode(name)) && val.specified ?
				val.value :
				null;
};

Sizzle.error = function( msg ) {
	throw new Error( "Syntax error, unrecognized expression: " + msg );
};

/**
 * Document sorting and removing duplicates
 * @param {ArrayLike} results
 */
Sizzle.uniqueSort = function( results ) {
	var elem,
		duplicates = [],
		j = 0,
		i = 0;

	// Unless we *know* we can detect duplicates, assume their presence
	hasDuplicate = !support.detectDuplicates;
	sortInput = !support.sortStable && results.slice( 0 );
	results.sort( sortOrder );

	if ( hasDuplicate ) {
		while ( (elem = results[i++]) ) {
			if ( elem === results[ i ] ) {
				j = duplicates.push( i );
			}
		}
		while ( j-- ) {
			results.splice( duplicates[ j ], 1 );
		}
	}

	// Clear input after sorting to release objects
	// See https://github.com/jquery/sizzle/pull/225
	sortInput = null;

	return results;
};

/**
 * Utility function for retrieving the text value of an array of DOM nodes
 * @param {Array|Element} elem
 */
getText = Sizzle.getText = function( elem ) {
	var node,
		ret = "",
		i = 0,
		nodeType = elem.nodeType;

	if ( !nodeType ) {
		// If no nodeType, this is expected to be an array
		while ( (node = elem[i++]) ) {
			// Do not traverse comment nodes
			ret += getText( node );
		}
	} else if ( nodeType === 1 || nodeType === 9 || nodeType === 11 ) {
		// Use textContent for elements
		// innerText usage removed for consistency of new lines (jQuery #11153)
		if ( typeof elem.textContent === "string" ) {
			return elem.textContent;
		} else {
			// Traverse its children
			for ( elem = elem.firstChild; elem; elem = elem.nextSibling ) {
				ret += getText( elem );
			}
		}
	} else if ( nodeType === 3 || nodeType === 4 ) {
		return elem.nodeValue;
	}
	// Do not include comment or processing instruction nodes

	return ret;
};

Expr = Sizzle.selectors = {

	// Can be adjusted by the user
	cacheLength: 50,

	createPseudo: markFunction,

	match: matchExpr,

	attrHandle: {},

	find: {},

	relative: {
		">": { dir: "parentNode", first: true },
		" ": { dir: "parentNode" },
		"+": { dir: "previousSibling", first: true },
		"~": { dir: "previousSibling" }
	},

	preFilter: {
		"ATTR": function( match ) {
			match[1] = match[1].replace( runescape, funescape );

			// Move the given value to match[3] whether quoted or unquoted
			match[3] = ( match[3] || match[4] || match[5] || "" ).replace( runescape, funescape );

			if ( match[2] === "~=" ) {
				match[3] = " " + match[3] + " ";
			}

			return match.slice( 0, 4 );
		},

		"CHILD": function( match ) {
			/* matches from matchExpr["CHILD"]
				1 type (only|nth|...)
				2 what (child|of-type)
				3 argument (even|odd|\d*|\d*n([+-]\d+)?|...)
				4 xn-component of xn+y argument ([+-]?\d*n|)
				5 sign of xn-component
				6 x of xn-component
				7 sign of y-component
				8 y of y-component
			*/
			match[1] = match[1].toLowerCase();

			if ( match[1].slice( 0, 3 ) === "nth" ) {
				// nth-* requires argument
				if ( !match[3] ) {
					Sizzle.error( match[0] );
				}

				// numeric x and y parameters for Expr.filter.CHILD
				// remember that false/true cast respectively to 0/1
				match[4] = +( match[4] ? match[5] + (match[6] || 1) : 2 * ( match[3] === "even" || match[3] === "odd" ) );
				match[5] = +( ( match[7] + match[8] ) || match[3] === "odd" );

			// other types prohibit arguments
			} else if ( match[3] ) {
				Sizzle.error( match[0] );
			}

			return match;
		},

		"PSEUDO": function( match ) {
			var excess,
				unquoted = !match[6] && match[2];

			if ( matchExpr["CHILD"].test( match[0] ) ) {
				return null;
			}

			// Accept quoted arguments as-is
			if ( match[3] ) {
				match[2] = match[4] || match[5] || "";

			// Strip excess characters from unquoted arguments
			} else if ( unquoted && rpseudo.test( unquoted ) &&
				// Get excess from tokenize (recursively)
				(excess = tokenize( unquoted, true )) &&
				// advance to the next closing parenthesis
				(excess = unquoted.indexOf( ")", unquoted.length - excess ) - unquoted.length) ) {

				// excess is a negative index
				match[0] = match[0].slice( 0, excess );
				match[2] = unquoted.slice( 0, excess );
			}

			// Return only captures needed by the pseudo filter method (type and argument)
			return match.slice( 0, 3 );
		}
	},

	filter: {

		"TAG": function( nodeNameSelector ) {
			var nodeName = nodeNameSelector.replace( runescape, funescape ).toLowerCase();
			return nodeNameSelector === "*" ?
				function() { return true; } :
				function( elem ) {
					return elem.nodeName && elem.nodeName.toLowerCase() === nodeName;
				};
		},

		"CLASS": function( className ) {
			var pattern = classCache[ className + " " ];

			return pattern ||
				(pattern = new RegExp( "(^|" + whitespace + ")" + className + "(" + whitespace + "|$)" )) &&
				classCache( className, function( elem ) {
					return pattern.test( typeof elem.className === "string" && elem.className || typeof elem.getAttribute !== "undefined" && elem.getAttribute("class") || "" );
				});
		},

		"ATTR": function( name, operator, check ) {
			return function( elem ) {
				var result = Sizzle.attr( elem, name );

				if ( result == null ) {
					return operator === "!=";
				}
				if ( !operator ) {
					return true;
				}

				result += "";

				return operator === "=" ? result === check :
					operator === "!=" ? result !== check :
					operator === "^=" ? check && result.indexOf( check ) === 0 :
					operator === "*=" ? check && result.indexOf( check ) > -1 :
					operator === "$=" ? check && result.slice( -check.length ) === check :
					operator === "~=" ? ( " " + result.replace( rwhitespace, " " ) + " " ).indexOf( check ) > -1 :
					operator === "|=" ? result === check || result.slice( 0, check.length + 1 ) === check + "-" :
					false;
			};
		},

		"CHILD": function( type, what, argument, first, last ) {
			var simple = type.slice( 0, 3 ) !== "nth",
				forward = type.slice( -4 ) !== "last",
				ofType = what === "of-type";

			return first === 1 && last === 0 ?

				// Shortcut for :nth-*(n)
				function( elem ) {
					return !!elem.parentNode;
				} :

				function( elem, context, xml ) {
					var cache, outerCache, node, diff, nodeIndex, start,
						dir = simple !== forward ? "nextSibling" : "previousSibling",
						parent = elem.parentNode,
						name = ofType && elem.nodeName.toLowerCase(),
						useCache = !xml && !ofType;

					if ( parent ) {

						// :(first|last|only)-(child|of-type)
						if ( simple ) {
							while ( dir ) {
								node = elem;
								while ( (node = node[ dir ]) ) {
									if ( ofType ? node.nodeName.toLowerCase() === name : node.nodeType === 1 ) {
										return false;
									}
								}
								// Reverse direction for :only-* (if we haven't yet done so)
								start = dir = type === "only" && !start && "nextSibling";
							}
							return true;
						}

						start = [ forward ? parent.firstChild : parent.lastChild ];

						// non-xml :nth-child(...) stores cache data on `parent`
						if ( forward && useCache ) {
							// Seek `elem` from a previously-cached index
							outerCache = parent[ expando ] || (parent[ expando ] = {});
							cache = outerCache[ type ] || [];
							nodeIndex = cache[0] === dirruns && cache[1];
							diff = cache[0] === dirruns && cache[2];
							node = nodeIndex && parent.childNodes[ nodeIndex ];

							while ( (node = ++nodeIndex && node && node[ dir ] ||

								// Fallback to seeking `elem` from the start
								(diff = nodeIndex = 0) || start.pop()) ) {

								// When found, cache indexes on `parent` and break
								if ( node.nodeType === 1 && ++diff && node === elem ) {
									outerCache[ type ] = [ dirruns, nodeIndex, diff ];
									break;
								}
							}

						// Use previously-cached element index if available
						} else if ( useCache && (cache = (elem[ expando ] || (elem[ expando ] = {}))[ type ]) && cache[0] === dirruns ) {
							diff = cache[1];

						// xml :nth-child(...) or :nth-last-child(...) or :nth(-last)?-of-type(...)
						} else {
							// Use the same loop as above to seek `elem` from the start
							while ( (node = ++nodeIndex && node && node[ dir ] ||
								(diff = nodeIndex = 0) || start.pop()) ) {

								if ( ( ofType ? node.nodeName.toLowerCase() === name : node.nodeType === 1 ) && ++diff ) {
									// Cache the index of each encountered element
									if ( useCache ) {
										(node[ expando ] || (node[ expando ] = {}))[ type ] = [ dirruns, diff ];
									}

									if ( node === elem ) {
										break;
									}
								}
							}
						}

						// Incorporate the offset, then check against cycle size
						diff -= last;
						return diff === first || ( diff % first === 0 && diff / first >= 0 );
					}
				};
		},

		"PSEUDO": function( pseudo, argument ) {
			// pseudo-class names are case-insensitive
			// http://www.w3.org/TR/selectors/#pseudo-classes
			// Prioritize by case sensitivity in case custom pseudos are added with uppercase letters
			// Remember that setFilters inherits from pseudos
			var args,
				fn = Expr.pseudos[ pseudo ] || Expr.setFilters[ pseudo.toLowerCase() ] ||
					Sizzle.error( "unsupported pseudo: " + pseudo );

			// The user may use createPseudo to indicate that
			// arguments are needed to create the filter function
			// just as Sizzle does
			if ( fn[ expando ] ) {
				return fn( argument );
			}

			// But maintain support for old signatures
			if ( fn.length > 1 ) {
				args = [ pseudo, pseudo, "", argument ];
				return Expr.setFilters.hasOwnProperty( pseudo.toLowerCase() ) ?
					markFunction(function( seed, matches ) {
						var idx,
							matched = fn( seed, argument ),
							i = matched.length;
						while ( i-- ) {
							idx = indexOf( seed, matched[i] );
							seed[ idx ] = !( matches[ idx ] = matched[i] );
						}
					}) :
					function( elem ) {
						return fn( elem, 0, args );
					};
			}

			return fn;
		}
	},

	pseudos: {
		// Potentially complex pseudos
		"not": markFunction(function( selector ) {
			// Trim the selector passed to compile
			// to avoid treating leading and trailing
			// spaces as combinators
			var input = [],
				results = [],
				matcher = compile( selector.replace( rtrim, "$1" ) );

			return matcher[ expando ] ?
				markFunction(function( seed, matches, context, xml ) {
					var elem,
						unmatched = matcher( seed, null, xml, [] ),
						i = seed.length;

					// Match elements unmatched by `matcher`
					while ( i-- ) {
						if ( (elem = unmatched[i]) ) {
							seed[i] = !(matches[i] = elem);
						}
					}
				}) :
				function( elem, context, xml ) {
					input[0] = elem;
					matcher( input, null, xml, results );
					// Don't keep the element (issue #299)
					input[0] = null;
					return !results.pop();
				};
		}),

		"has": markFunction(function( selector ) {
			return function( elem ) {
				return Sizzle( selector, elem ).length > 0;
			};
		}),

		"contains": markFunction(function( text ) {
			text = text.replace( runescape, funescape );
			return function( elem ) {
				return ( elem.textContent || elem.innerText || getText( elem ) ).indexOf( text ) > -1;
			};
		}),

		// "Whether an element is represented by a :lang() selector
		// is based solely on the element's language value
		// being equal to the identifier C,
		// or beginning with the identifier C immediately followed by "-".
		// The matching of C against the element's language value is performed case-insensitively.
		// The identifier C does not have to be a valid language name."
		// http://www.w3.org/TR/selectors/#lang-pseudo
		"lang": markFunction( function( lang ) {
			// lang value must be a valid identifier
			if ( !ridentifier.test(lang || "") ) {
				Sizzle.error( "unsupported lang: " + lang );
			}
			lang = lang.replace( runescape, funescape ).toLowerCase();
			return function( elem ) {
				var elemLang;
				do {
					if ( (elemLang = documentIsHTML ?
						elem.lang :
						elem.getAttribute("xml:lang") || elem.getAttribute("lang")) ) {

						elemLang = elemLang.toLowerCase();
						return elemLang === lang || elemLang.indexOf( lang + "-" ) === 0;
					}
				} while ( (elem = elem.parentNode) && elem.nodeType === 1 );
				return false;
			};
		}),

		// Miscellaneous
		"target": function( elem ) {
			var hash = window.location && window.location.hash;
			return hash && hash.slice( 1 ) === elem.id;
		},

		"root": function( elem ) {
			return elem === docElem;
		},

		"focus": function( elem ) {
			return elem === document.activeElement && (!document.hasFocus || document.hasFocus()) && !!(elem.type || elem.href || ~elem.tabIndex);
		},

		// Boolean properties
		"enabled": function( elem ) {
			return elem.disabled === false;
		},

		"disabled": function( elem ) {
			return elem.disabled === true;
		},

		"checked": function( elem ) {
			// In CSS3, :checked should return both checked and selected elements
			// http://www.w3.org/TR/2011/REC-css3-selectors-20110929/#checked
			var nodeName = elem.nodeName.toLowerCase();
			return (nodeName === "input" && !!elem.checked) || (nodeName === "option" && !!elem.selected);
		},

		"selected": function( elem ) {
			// Accessing this property makes selected-by-default
			// options in Safari work properly
			if ( elem.parentNode ) {
				elem.parentNode.selectedIndex;
			}

			return elem.selected === true;
		},

		// Contents
		"empty": function( elem ) {
			// http://www.w3.org/TR/selectors/#empty-pseudo
			// :empty is negated by element (1) or content nodes (text: 3; cdata: 4; entity ref: 5),
			//   but not by others (comment: 8; processing instruction: 7; etc.)
			// nodeType < 6 works because attributes (2) do not appear as children
			for ( elem = elem.firstChild; elem; elem = elem.nextSibling ) {
				if ( elem.nodeType < 6 ) {
					return false;
				}
			}
			return true;
		},

		"parent": function( elem ) {
			return !Expr.pseudos["empty"]( elem );
		},

		// Element/input types
		"header": function( elem ) {
			return rheader.test( elem.nodeName );
		},

		"input": function( elem ) {
			return rinputs.test( elem.nodeName );
		},

		"button": function( elem ) {
			var name = elem.nodeName.toLowerCase();
			return name === "input" && elem.type === "button" || name === "button";
		},

		"text": function( elem ) {
			var attr;
			return elem.nodeName.toLowerCase() === "input" &&
				elem.type === "text" &&

				// Support: IE<8
				// New HTML5 attribute values (e.g., "search") appear with elem.type === "text"
				( (attr = elem.getAttribute("type")) == null || attr.toLowerCase() === "text" );
		},

		// Position-in-collection
		"first": createPositionalPseudo(function() {
			return [ 0 ];
		}),

		"last": createPositionalPseudo(function( matchIndexes, length ) {
			return [ length - 1 ];
		}),

		"eq": createPositionalPseudo(function( matchIndexes, length, argument ) {
			return [ argument < 0 ? argument + length : argument ];
		}),

		"even": createPositionalPseudo(function( matchIndexes, length ) {
			var i = 0;
			for ( ; i < length; i += 2 ) {
				matchIndexes.push( i );
			}
			return matchIndexes;
		}),

		"odd": createPositionalPseudo(function( matchIndexes, length ) {
			var i = 1;
			for ( ; i < length; i += 2 ) {
				matchIndexes.push( i );
			}
			return matchIndexes;
		}),

		"lt": createPositionalPseudo(function( matchIndexes, length, argument ) {
			var i = argument < 0 ? argument + length : argument;
			for ( ; --i >= 0; ) {
				matchIndexes.push( i );
			}
			return matchIndexes;
		}),

		"gt": createPositionalPseudo(function( matchIndexes, length, argument ) {
			var i = argument < 0 ? argument + length : argument;
			for ( ; ++i < length; ) {
				matchIndexes.push( i );
			}
			return matchIndexes;
		})
	}
};

Expr.pseudos["nth"] = Expr.pseudos["eq"];

// Add button/input type pseudos
for ( i in { radio: true, checkbox: true, file: true, password: true, image: true } ) {
	Expr.pseudos[ i ] = createInputPseudo( i );
}
for ( i in { submit: true, reset: true } ) {
	Expr.pseudos[ i ] = createButtonPseudo( i );
}

// Easy API for creating new setFilters
function setFilters() {}
setFilters.prototype = Expr.filters = Expr.pseudos;
Expr.setFilters = new setFilters();

tokenize = Sizzle.tokenize = function( selector, parseOnly ) {
	var matched, match, tokens, type,
		soFar, groups, preFilters,
		cached = tokenCache[ selector + " " ];

	if ( cached ) {
		return parseOnly ? 0 : cached.slice( 0 );
	}

	soFar = selector;
	groups = [];
	preFilters = Expr.preFilter;

	while ( soFar ) {

		// Comma and first run
		if ( !matched || (match = rcomma.exec( soFar )) ) {
			if ( match ) {
				// Don't consume trailing commas as valid
				soFar = soFar.slice( match[0].length ) || soFar;
			}
			groups.push( (tokens = []) );
		}

		matched = false;

		// Combinators
		if ( (match = rcombinators.exec( soFar )) ) {
			matched = match.shift();
			tokens.push({
				value: matched,
				// Cast descendant combinators to space
				type: match[0].replace( rtrim, " " )
			});
			soFar = soFar.slice( matched.length );
		}

		// Filters
		for ( type in Expr.filter ) {
			if ( (match = matchExpr[ type ].exec( soFar )) && (!preFilters[ type ] ||
				(match = preFilters[ type ]( match ))) ) {
				matched = match.shift();
				tokens.push({
					value: matched,
					type: type,
					matches: match
				});
				soFar = soFar.slice( matched.length );
			}
		}

		if ( !matched ) {
			break;
		}
	}

	// Return the length of the invalid excess
	// if we're just parsing
	// Otherwise, throw an error or return tokens
	return parseOnly ?
		soFar.length :
		soFar ?
			Sizzle.error( selector ) :
			// Cache the tokens
			tokenCache( selector, groups ).slice( 0 );
};

function toSelector( tokens ) {
	var i = 0,
		len = tokens.length,
		selector = "";
	for ( ; i < len; i++ ) {
		selector += tokens[i].value;
	}
	return selector;
}

function addCombinator( matcher, combinator, base ) {
	var dir = combinator.dir,
		checkNonElements = base && dir === "parentNode",
		doneName = done++;

	return combinator.first ?
		// Check against closest ancestor/preceding element
		function( elem, context, xml ) {
			while ( (elem = elem[ dir ]) ) {
				if ( elem.nodeType === 1 || checkNonElements ) {
					return matcher( elem, context, xml );
				}
			}
		} :

		// Check against all ancestor/preceding elements
		function( elem, context, xml ) {
			var oldCache, outerCache,
				newCache = [ dirruns, doneName ];

			// We can't set arbitrary data on XML nodes, so they don't benefit from dir caching
			if ( xml ) {
				while ( (elem = elem[ dir ]) ) {
					if ( elem.nodeType === 1 || checkNonElements ) {
						if ( matcher( elem, context, xml ) ) {
							return true;
						}
					}
				}
			} else {
				while ( (elem = elem[ dir ]) ) {
					if ( elem.nodeType === 1 || checkNonElements ) {
						outerCache = elem[ expando ] || (elem[ expando ] = {});
						if ( (oldCache = outerCache[ dir ]) &&
							oldCache[ 0 ] === dirruns && oldCache[ 1 ] === doneName ) {

							// Assign to newCache so results back-propagate to previous elements
							return (newCache[ 2 ] = oldCache[ 2 ]);
						} else {
							// Reuse newcache so results back-propagate to previous elements
							outerCache[ dir ] = newCache;

							// A match means we're done; a fail means we have to keep checking
							if ( (newCache[ 2 ] = matcher( elem, context, xml )) ) {
								return true;
							}
						}
					}
				}
			}
		};
}

function elementMatcher( matchers ) {
	return matchers.length > 1 ?
		function( elem, context, xml ) {
			var i = matchers.length;
			while ( i-- ) {
				if ( !matchers[i]( elem, context, xml ) ) {
					return false;
				}
			}
			return true;
		} :
		matchers[0];
}

function multipleContexts( selector, contexts, results ) {
	var i = 0,
		len = contexts.length;
	for ( ; i < len; i++ ) {
		Sizzle( selector, contexts[i], results );
	}
	return results;
}

function condense( unmatched, map, filter, context, xml ) {
	var elem,
		newUnmatched = [],
		i = 0,
		len = unmatched.length,
		mapped = map != null;

	for ( ; i < len; i++ ) {
		if ( (elem = unmatched[i]) ) {
			if ( !filter || filter( elem, context, xml ) ) {
				newUnmatched.push( elem );
				if ( mapped ) {
					map.push( i );
				}
			}
		}
	}

	return newUnmatched;
}

function setMatcher( preFilter, selector, matcher, postFilter, postFinder, postSelector ) {
	if ( postFilter && !postFilter[ expando ] ) {
		postFilter = setMatcher( postFilter );
	}
	if ( postFinder && !postFinder[ expando ] ) {
		postFinder = setMatcher( postFinder, postSelector );
	}
	return markFunction(function( seed, results, context, xml ) {
		var temp, i, elem,
			preMap = [],
			postMap = [],
			preexisting = results.length,

			// Get initial elements from seed or context
			elems = seed || multipleContexts( selector || "*", context.nodeType ? [ context ] : context, [] ),

			// Prefilter to get matcher input, preserving a map for seed-results synchronization
			matcherIn = preFilter && ( seed || !selector ) ?
				condense( elems, preMap, preFilter, context, xml ) :
				elems,

			matcherOut = matcher ?
				// If we have a postFinder, or filtered seed, or non-seed postFilter or preexisting results,
				postFinder || ( seed ? preFilter : preexisting || postFilter ) ?

					// ...intermediate processing is necessary
					[] :

					// ...otherwise use results directly
					results :
				matcherIn;

		// Find primary matches
		if ( matcher ) {
			matcher( matcherIn, matcherOut, context, xml );
		}

		// Apply postFilter
		if ( postFilter ) {
			temp = condense( matcherOut, postMap );
			postFilter( temp, [], context, xml );

			// Un-match failing elements by moving them back to matcherIn
			i = temp.length;
			while ( i-- ) {
				if ( (elem = temp[i]) ) {
					matcherOut[ postMap[i] ] = !(matcherIn[ postMap[i] ] = elem);
				}
			}
		}

		if ( seed ) {
			if ( postFinder || preFilter ) {
				if ( postFinder ) {
					// Get the final matcherOut by condensing this intermediate into postFinder contexts
					temp = [];
					i = matcherOut.length;
					while ( i-- ) {
						if ( (elem = matcherOut[i]) ) {
							// Restore matcherIn since elem is not yet a final match
							temp.push( (matcherIn[i] = elem) );
						}
					}
					postFinder( null, (matcherOut = []), temp, xml );
				}

				// Move matched elements from seed to results to keep them synchronized
				i = matcherOut.length;
				while ( i-- ) {
					if ( (elem = matcherOut[i]) &&
						(temp = postFinder ? indexOf( seed, elem ) : preMap[i]) > -1 ) {

						seed[temp] = !(results[temp] = elem);
					}
				}
			}

		// Add elements to results, through postFinder if defined
		} else {
			matcherOut = condense(
				matcherOut === results ?
					matcherOut.splice( preexisting, matcherOut.length ) :
					matcherOut
			);
			if ( postFinder ) {
				postFinder( null, results, matcherOut, xml );
			} else {
				push.apply( results, matcherOut );
			}
		}
	});
}

function matcherFromTokens( tokens ) {
	var checkContext, matcher, j,
		len = tokens.length,
		leadingRelative = Expr.relative[ tokens[0].type ],
		implicitRelative = leadingRelative || Expr.relative[" "],
		i = leadingRelative ? 1 : 0,

		// The foundational matcher ensures that elements are reachable from top-level context(s)
		matchContext = addCombinator( function( elem ) {
			return elem === checkContext;
		}, implicitRelative, true ),
		matchAnyContext = addCombinator( function( elem ) {
			return indexOf( checkContext, elem ) > -1;
		}, implicitRelative, true ),
		matchers = [ function( elem, context, xml ) {
			var ret = ( !leadingRelative && ( xml || context !== outermostContext ) ) || (
				(checkContext = context).nodeType ?
					matchContext( elem, context, xml ) :
					matchAnyContext( elem, context, xml ) );
			// Avoid hanging onto element (issue #299)
			checkContext = null;
			return ret;
		} ];

	for ( ; i < len; i++ ) {
		if ( (matcher = Expr.relative[ tokens[i].type ]) ) {
			matchers = [ addCombinator(elementMatcher( matchers ), matcher) ];
		} else {
			matcher = Expr.filter[ tokens[i].type ].apply( null, tokens[i].matches );

			// Return special upon seeing a positional matcher
			if ( matcher[ expando ] ) {
				// Find the next relative operator (if any) for proper handling
				j = ++i;
				for ( ; j < len; j++ ) {
					if ( Expr.relative[ tokens[j].type ] ) {
						break;
					}
				}
				return setMatcher(
					i > 1 && elementMatcher( matchers ),
					i > 1 && toSelector(
						// If the preceding token was a descendant combinator, insert an implicit any-element `*`
						tokens.slice( 0, i - 1 ).concat({ value: tokens[ i - 2 ].type === " " ? "*" : "" })
					).replace( rtrim, "$1" ),
					matcher,
					i < j && matcherFromTokens( tokens.slice( i, j ) ),
					j < len && matcherFromTokens( (tokens = tokens.slice( j )) ),
					j < len && toSelector( tokens )
				);
			}
			matchers.push( matcher );
		}
	}

	return elementMatcher( matchers );
}

function matcherFromGroupMatchers( elementMatchers, setMatchers ) {
	var bySet = setMatchers.length > 0,
		byElement = elementMatchers.length > 0,
		superMatcher = function( seed, context, xml, results, outermost ) {
			var elem, j, matcher,
				matchedCount = 0,
				i = "0",
				unmatched = seed && [],
				setMatched = [],
				contextBackup = outermostContext,
				// We must always have either seed elements or outermost context
				elems = seed || byElement && Expr.find["TAG"]( "*", outermost ),
				// Use integer dirruns iff this is the outermost matcher
				dirrunsUnique = (dirruns += contextBackup == null ? 1 : Math.random() || 0.1),
				len = elems.length;

			if ( outermost ) {
				outermostContext = context !== document && context;
			}

			// Add elements passing elementMatchers directly to results
			// Keep `i` a string if there are no elements so `matchedCount` will be "00" below
			// Support: IE<9, Safari
			// Tolerate NodeList properties (IE: "length"; Safari: <number>) matching elements by id
			for ( ; i !== len && (elem = elems[i]) != null; i++ ) {
				if ( byElement && elem ) {
					j = 0;
					while ( (matcher = elementMatchers[j++]) ) {
						if ( matcher( elem, context, xml ) ) {
							results.push( elem );
							break;
						}
					}
					if ( outermost ) {
						dirruns = dirrunsUnique;
					}
				}

				// Track unmatched elements for set filters
				if ( bySet ) {
					// They will have gone through all possible matchers
					if ( (elem = !matcher && elem) ) {
						matchedCount--;
					}

					// Lengthen the array for every element, matched or not
					if ( seed ) {
						unmatched.push( elem );
					}
				}
			}

			// Apply set filters to unmatched elements
			matchedCount += i;
			if ( bySet && i !== matchedCount ) {
				j = 0;
				while ( (matcher = setMatchers[j++]) ) {
					matcher( unmatched, setMatched, context, xml );
				}

				if ( seed ) {
					// Reintegrate element matches to eliminate the need for sorting
					if ( matchedCount > 0 ) {
						while ( i-- ) {
							if ( !(unmatched[i] || setMatched[i]) ) {
								setMatched[i] = pop.call( results );
							}
						}
					}

					// Discard index placeholder values to get only actual matches
					setMatched = condense( setMatched );
				}

				// Add matches to results
				push.apply( results, setMatched );

				// Seedless set matches succeeding multiple successful matchers stipulate sorting
				if ( outermost && !seed && setMatched.length > 0 &&
					( matchedCount + setMatchers.length ) > 1 ) {

					Sizzle.uniqueSort( results );
				}
			}

			// Override manipulation of globals by nested matchers
			if ( outermost ) {
				dirruns = dirrunsUnique;
				outermostContext = contextBackup;
			}

			return unmatched;
		};

	return bySet ?
		markFunction( superMatcher ) :
		superMatcher;
}

compile = Sizzle.compile = function( selector, match /* Internal Use Only */ ) {
	var i,
		setMatchers = [],
		elementMatchers = [],
		cached = compilerCache[ selector + " " ];

	if ( !cached ) {
		// Generate a function of recursive functions that can be used to check each element
		if ( !match ) {
			match = tokenize( selector );
		}
		i = match.length;
		while ( i-- ) {
			cached = matcherFromTokens( match[i] );
			if ( cached[ expando ] ) {
				setMatchers.push( cached );
			} else {
				elementMatchers.push( cached );
			}
		}

		// Cache the compiled function
		cached = compilerCache( selector, matcherFromGroupMatchers( elementMatchers, setMatchers ) );

		// Save selector and tokenization
		cached.selector = selector;
	}
	return cached;
};

/**
 * A low-level selection function that works with Sizzle's compiled
 *  selector functions
 * @param {String|Function} selector A selector or a pre-compiled
 *  selector function built with Sizzle.compile
 * @param {Element} context
 * @param {Array} [results]
 * @param {Array} [seed] A set of elements to match against
 */
select = Sizzle.select = function( selector, context, results, seed ) {
	var i, tokens, token, type, find,
		compiled = typeof selector === "function" && selector,
		match = !seed && tokenize( (selector = compiled.selector || selector) );

	results = results || [];

	// Try to minimize operations if there is no seed and only one group
	if ( match.length === 1 ) {

		// Take a shortcut and set the context if the root selector is an ID
		tokens = match[0] = match[0].slice( 0 );
		if ( tokens.length > 2 && (token = tokens[0]).type === "ID" &&
				support.getById && context.nodeType === 9 && documentIsHTML &&
				Expr.relative[ tokens[1].type ] ) {

			context = ( Expr.find["ID"]( token.matches[0].replace(runescape, funescape), context ) || [] )[0];
			if ( !context ) {
				return results;

			// Precompiled matchers will still verify ancestry, so step up a level
			} else if ( compiled ) {
				context = context.parentNode;
			}

			selector = selector.slice( tokens.shift().value.length );
		}

		// Fetch a seed set for right-to-left matching
		i = matchExpr["needsContext"].test( selector ) ? 0 : tokens.length;
		while ( i-- ) {
			token = tokens[i];

			// Abort if we hit a combinator
			if ( Expr.relative[ (type = token.type) ] ) {
				break;
			}
			if ( (find = Expr.find[ type ]) ) {
				// Search, expanding context for leading sibling combinators
				if ( (seed = find(
					token.matches[0].replace( runescape, funescape ),
					rsibling.test( tokens[0].type ) && testContext( context.parentNode ) || context
				)) ) {

					// If seed is empty or no tokens remain, we can return early
					tokens.splice( i, 1 );
					selector = seed.length && toSelector( tokens );
					if ( !selector ) {
						push.apply( results, seed );
						return results;
					}

					break;
				}
			}
		}
	}

	// Compile and execute a filtering function if one is not provided
	// Provide `match` to avoid retokenization if we modified the selector above
	( compiled || compile( selector, match ) )(
		seed,
		context,
		!documentIsHTML,
		results,
		rsibling.test( selector ) && testContext( context.parentNode ) || context
	);
	return results;
};

// One-time assignments

// Sort stability
support.sortStable = expando.split("").sort( sortOrder ).join("") === expando;

// Support: Chrome 14-35+
// Always assume duplicates if they aren't passed to the comparison function
support.detectDuplicates = !!hasDuplicate;

// Initialize against the default document
setDocument();

// Support: Webkit<537.32 - Safari 6.0.3/Chrome 25 (fixed in Chrome 27)
// Detached nodes confoundingly follow *each other*
support.sortDetached = assert(function( div1 ) {
	// Should return 1, but returns 4 (following)
	return div1.compareDocumentPosition( document.createElement("div") ) & 1;
});

// Support: IE<8
// Prevent attribute/property "interpolation"
// http://msdn.microsoft.com/en-us/library/ms536429%28VS.85%29.aspx
if ( !assert(function( div ) {
	div.innerHTML = "<a href='#'></a>";
	return div.firstChild.getAttribute("href") === "#" ;
}) ) {
	addHandle( "type|href|height|width", function( elem, name, isXML ) {
		if ( !isXML ) {
			return elem.getAttribute( name, name.toLowerCase() === "type" ? 1 : 2 );
		}
	});
}

// Support: IE<9
// Use defaultValue in place of getAttribute("value")
if ( !support.attributes || !assert(function( div ) {
	div.innerHTML = "<input/>";
	div.firstChild.setAttribute( "value", "" );
	return div.firstChild.getAttribute( "value" ) === "";
}) ) {
	addHandle( "value", function( elem, name, isXML ) {
		if ( !isXML && elem.nodeName.toLowerCase() === "input" ) {
			return elem.defaultValue;
		}
	});
}

// Support: IE<9
// Use getAttributeNode to fetch booleans when getAttribute lies
if ( !assert(function( div ) {
	return div.getAttribute("disabled") == null;
}) ) {
	addHandle( booleans, function( elem, name, isXML ) {
		var val;
		if ( !isXML ) {
			return elem[ name ] === true ? name.toLowerCase() :
					(val = elem.getAttributeNode( name )) && val.specified ?
					val.value :
				null;
		}
	});
}

return Sizzle;

})( window );



	jTeddy.find = Sizzle;
	jTeddy.expr = Sizzle.selectors;
	jTeddy.expr[":"] = jTeddy.expr.pseudos;
	jTeddy.unique = Sizzle.uniqueSort;
	jTeddy.text = Sizzle.getText;
	jTeddy.isXMLDoc = Sizzle.isXML;
	jTeddy.contains = Sizzle.contains;



var rnotwhite = (/\S+/g);



// Convert String-formatted options into Object-formatted ones
	function createOptions(options) {
		var object = {};
		jTeddy.each(options.match(rnotwhite) || [], function (_, flag) {
			object[flag] = true;
		});
		return object;
	}

	/*
	 * Create a callback list using the following parameters:
	 *
	 *	options: an optional list of space-separated options that will change how
	 *			the callback list behaves or a more traditional option object
	 *
	 * By default a callback list will act like an event callback list and can be
	 * "fired" multiple times.
	 *
	 * Possible options:
	 *
	 *	once:			will ensure the callback list can only be fired once (like a Deferred)
	 *
	 *	memory:			will keep track of previous values and will call any callback added
	 *					after the list has been fired right away with the latest "memorized"
	 *					values (like a Deferred)
	 *
	 *	unique:			will ensure a callback can only be added once (no duplicate in the list)
	 *
	 *	stopOnFalse:	interrupt callings when a callback returns false
	 *
	 */
	jTeddy.Callbacks = function (options) {

		// Convert options from String-formatted to Object-formatted if needed
		// (we check in cache first)
		options = typeof options === "string" ?
			createOptions(options) :
			jTeddy.extend({}, options);

		var // Flag to know if list is currently firing
			firing,
		// Last fire value for non-forgettable lists
			memory,
		// Flag to know if list was already fired
			fired,
		// Flag to prevent firing
			locked,
		// Actual callback list
			list = [],
		// Queue of execution data for repeatable lists
			queue = [],
		// Index of currently firing callback (modified by add/remove as needed)
			firingIndex = -1,
		// Fire callbacks
			fire = function () {

				// Enforce single-firing
				locked = options.once;

				// Execute callbacks for all pending executions,
				// respecting firingIndex overrides and runtime changes
				fired = firing = true;
				for (; queue.length; firingIndex = -1) {
					memory = queue.shift();
					while (++firingIndex < list.length) {

						// Run callback and check for early termination
						if (list[firingIndex].apply(memory[0], memory[1]) === false &&
							options.stopOnFalse) {

							// Jump to end and forget the data so .add doesn't re-fire
							firingIndex = list.length;
							memory = false;
						}
					}
				}

				// Forget the data if we're done with it
				if (!options.memory) {
					memory = false;
				}

				firing = false;

				// Clean up if we're done firing for good
				if (locked) {

					// Keep an empty list if we have data for future add calls
					if (memory) {
						list = [];

						// Otherwise, this object is spent
					} else {
						list = "";
					}
				}
			},

		// Actual Callbacks object
			self = {

				// Add a callback or a collection of callbacks to the list
				add: function () {
					if (list) {

						// If we have memory from a past run, we should fire after adding
						if (memory && !firing) {
							firingIndex = list.length - 1;
							queue.push(memory);
						}

						(function add(args) {
							jTeddy.each(args, function (_, arg) {
								if (jTeddy.isFunction(arg)) {
									if (!options.unique || !self.has(arg)) {
										list.push(arg);
									}
								} else if (arg && arg.length && jTeddy.type(arg) !== "string") {
									// Inspect recursively
									add(arg);
								}
							});
						})(arguments);

						if (memory && !firing) {
							fire();
						}
					}
					return this;
				},

				// Remove a callback from the list
				remove: function () {
					jTeddy.each(arguments, function (_, arg) {
						var index;
						while (( index = jTeddy.inArray(arg, list, index) ) > -1) {
							list.splice(index, 1);

							// Handle firing indexes
							if (index <= firingIndex) {
								firingIndex--;
							}
						}
					});
					return this;
				},

				// Check if a given callback is in the list.
				// If no argument is given, return whether or not list has callbacks attached.
				has: function (fn) {
					return fn ?
					jTeddy.inArray(fn, list) > -1 :
					list.length > 0;
				},

				// Remove all callbacks from the list
				empty: function () {
					if (list) {
						list = [];
					}
					return this;
				},

				// Disable .fire and .add
				// Abort any current/pending executions
				// Clear all callbacks and values
				disable: function () {
					locked = queue = [];
					list = memory = "";
					return this;
				},
				disabled: function () {
					return !list;
				},

				// Disable .fire
				// Also disable .add unless we have memory (since it would have no effect)
				// Abort any pending executions
				lock: function () {
					locked = queue = [];
					if (!memory && !firing) {
						list = memory = "";
					}
					return this;
				},
				locked: function () {
					return !!locked;
				},

				// Call all callbacks with the given context and arguments
				fireWith: function (context, args) {
					if (!locked) {
						args = args || [];
						args = [context, args.slice ? args.slice() : args];
						queue.push(args);
						if (!firing) {
							fire();
						}
					}
					return this;
				},

				// Call all the callbacks with the given arguments
				fire: function () {
					self.fireWith(this, arguments);
					return this;
				},

				// To know if the callbacks have already been called at least once
				fired: function () {
					return !!fired;
				}
			};

		return self;
	};


	function Identity(v) {
		return v;
	}

	function Thrower(ex) {
		throw ex;
	}

	jTeddy.extend({

		Deferred: function (func) {
			var tuples = [
					// action, add listener, callbacks, .then handlers, final state
					["resolve", "done", jTeddy.Callbacks("once memory"),
						jTeddy.Callbacks("once memory"), "resolved"],
					["reject", "fail", jTeddy.Callbacks("once memory"),
						jTeddy.Callbacks("once memory"), "rejected"],
					["notify", "progress", jTeddy.Callbacks("memory"),
						jTeddy.Callbacks("memory")]
				],
				state = "pending",
				promise = {
					state: function () {
						return state;
					},
					always: function () {
						deferred.done(arguments).fail(arguments);
						return this;
					},
					// Keep pipe for back-compat
					pipe: function (/* fnDone, fnFail, fnProgress */) {
						var fns = arguments;

						return jTeddy.Deferred(function (newDefer) {
							jTeddy.each(tuples, function (i, tuple) {
								var fn = jTeddy.isFunction(fns[i]) && fns[i];
								// deferred.done(function() { bind to newDefer or newDefer.resolve })
								// deferred.fail(function() { bind to newDefer or newDefer.reject })
								// deferred.progress(function() { bind to newDefer or newDefer.notify })
								deferred[tuple[1]](function () {
									var returned = fn && fn.apply(this, arguments);
									if (returned && jTeddy.isFunction(returned.promise)) {
										returned.promise()
											.done(newDefer.resolve)
											.fail(newDefer.reject)
											.progress(newDefer.notify);
									} else {
										newDefer[tuple[0] + "With"](
											this === promise ? newDefer.promise() : this,
											fn ? [returned] : arguments
										);
									}
								});
							});
							fns = null;
						}).promise();
					},
					then: function (onFulfilled, onRejected, onProgress) {
						var maxDepth = 0;

						function resolve(depth, deferred, handler, special) {
							return function () {
								var that = this === promise ? undefined : this,
									args = arguments,
									mightThrow = function () {
										var returned, then;

										// Support: Promises/A+ section 2.3.3.3.3
										// https://promisesaplus.com/#point-59
										// Ignore double-resolution attempts
										if (depth < maxDepth) {
											return;
										}

										returned = handler.apply(that, args);

										// Support: Promises/A+ section 2.3.1
										// https://promisesaplus.com/#point-48
										if (returned === deferred.promise()) {
											throw new TypeError("Thenable self-resolution");
										}

										// Support: Promises/A+ sections 2.3.3.1, 3.5
										// https://promisesaplus.com/#point-54
										// https://promisesaplus.com/#point-75
										// Retrieve `then` only once
										then = returned &&

											// Support: Promises/A+ section 2.3.4
											// https://promisesaplus.com/#point-64
											// Only check objects and functions for thenability
										( typeof returned === "object" ||
										typeof returned === "function" ) &&
										returned.then;

										// Handle a returned thenable
										if (jTeddy.isFunction(then)) {
											// Special processors (notify) just wait for resolution
											if (special) {
												then.call(
													returned,
													resolve(maxDepth, deferred, Identity, special),
													resolve(maxDepth, deferred, Thrower, special)
												);

												// Normal processors (resolve) also hook into progress
											} else {

												// ...and disregard older resolution values
												maxDepth++;

												then.call(
													returned,
													resolve(maxDepth, deferred, Identity, special),
													resolve(maxDepth, deferred, Thrower, special),
													resolve(maxDepth, deferred, Identity,
														deferred.notify)
												);
											}

											// Handle all other returned values
										} else {
											// Only substitue handlers pass on context
											// and multiple values (non-spec behavior)
											if (handler !== Identity) {
												that = undefined;
												args = [returned];
											}

											// Process the value(s)
											// Default process is resolve
											( special || deferred.resolveWith )(
												that || deferred.promise(), args);
										}
									},

								// Only normal processors (resolve) catch and reject exceptions
									process = special ?
										mightThrow :
										function () {
											try {
												mightThrow();
											} catch (e) {

												// Support: Promises/A+ section 2.3.3.3.4.1
												// https://promisesaplus.com/#point-61
												// Ignore post-resolution exceptions
												if (depth + 1 >= maxDepth) {
													// Only substitue handlers pass on context
													// and multiple values (non-spec behavior)
													if (handler !== Thrower) {
														that = undefined;
														args = [e];
													}

													deferred.rejectWith(that || deferred.promise(),
														args);
												}
											}
										};

								// Support: Promises/A+ section 2.3.3.3.1
								// https://promisesaplus.com/#point-57
								// Re-resolve promises immediately to dodge false rejection from
								// subsequent errors
								if (depth) {
									process();
								} else {
									setTimeout(process);
								}
							};
						}

						return jTeddy.Deferred(function (newDefer) {
							// fulfilled_handlers.add( ... )
							tuples[0][3].add(
								resolve(
									0,
									newDefer,
									jTeddy.isFunction(onFulfilled) ?
										onFulfilled :
										Identity
								)
							);

							// rejected_handlers.add( ... )
							tuples[1][3].add(
								resolve(
									0,
									newDefer,
									jTeddy.isFunction(onRejected) ?
										onRejected :
										Thrower
								)
							);

							// progress_handlers.add( ... )
							tuples[2][3].add(
								resolve(
									0,
									newDefer,
									jTeddy.isFunction(onProgress) ?
										onProgress :
										Identity,
									newDefer.notifyWith
								)
							);
						}).promise();
					},
					// Get a promise for this deferred
					// If obj is provided, the promise aspect is added to the object
					promise: function (obj) {
						return obj != null ? jTeddy.extend(obj, promise) : promise;
					}
				},
				deferred = {};

			// Add list-specific methods
			jTeddy.each(tuples, function (i, tuple) {
				var list = tuple[2],
					stateString = tuple[4];

				// promise.done = list.add
				// promise.fail = list.add
				// promise.progress = list.add
				promise[tuple[1]] = list.add;

				// Handle state
				if (stateString) {
					list.add(
						function () {
							// state = "resolved" (i.e., fulfilled)
							// state = "rejected"
							state = stateString;
						},

						// rejected_callbacks.disable
						// fulfilled_callbacks.disable
						tuples[i ^ 1][2].disable,

						// progress_callbacks.lock
						tuples[2][2].lock
					);
				}

				// fulfilled_handlers.fire
				// rejected_handlers.fire
				// progress_handlers.fire
				list.add(tuple[3].fire);

				// deferred.resolve = function() { deferred.resolveWith(...) }
				// deferred.reject = function() { deferred.rejectWith(...) }
				// deferred.notify = function() { deferred.notifyWith(...) }
				deferred[tuple[0]] = function () {
					deferred[tuple[0] + "With"](this === deferred ? promise : this, arguments);
					return this;
				};

				// deferred.resolveWith = list.fireWith
				// deferred.rejectWith = list.fireWith
				// deferred.notifyWith = list.fireWith
				deferred[tuple[0] + "With"] = list.fireWith;
			});

			// Make the deferred a promise
			promise.promise(deferred);

			// Call given func if any
			if (func) {
				func.call(deferred, deferred);
			}

			// All done!
			return deferred;
		},

		// Deferred helper
		when: function (subordinate /* , ..., subordinateN */) {
			var method,
				i = 0,
				resolveValues = slice.call(arguments),
				length = resolveValues.length,

			// the count of uncompleted subordinates
				remaining = length !== 1 ||
				( subordinate && jTeddy.isFunction(subordinate.promise) ) ? length : 0,

			// the master Deferred.
			// If resolveValues consist of only a single Deferred, just use that.
				master = remaining === 1 ? subordinate : jTeddy.Deferred(),

			// Update function for both resolve and progress values
				updateFunc = function (i, contexts, values) {
					return function (value) {
						contexts[i] = this;
						values[i] = arguments.length > 1 ? slice.call(arguments) : value;
						if (values === progressValues) {
							master.notifyWith(contexts, values);
						} else if (!( --remaining )) {
							master.resolveWith(contexts, values);
						}
					};
				},
				progressValues, progressContexts, resolveContexts;

			// Add listeners to Deferred subordinates; treat others as resolved
			if (length > 1) {
				progressValues = new Array(length);
				progressContexts = new Array(length);
				resolveContexts = new Array(length);
				for (; i < length; i++) {
					if (resolveValues[i] &&
						jTeddy.isFunction((method = resolveValues[i].promise))) {

						method.call(resolveValues[i])
							.progress(updateFunc(i, progressContexts, progressValues))
							.done(updateFunc(i, resolveContexts, resolveValues))
							.fail(master.reject);
					} else if (resolveValues[i] &&
						jTeddy.isFunction((method = resolveValues[i].then))) {

						method.call(
							resolveValues[i],
							updateFunc(i, resolveContexts, resolveValues),
							master.reject,
							updateFunc(i, progressContexts, progressValues)
						);
					} else {
						--remaining;
					}
				}
			}

			// If we're not waiting on anything, resolve the master
			if (!remaining) {
				master.resolveWith(resolveContexts, resolveValues);
			}

			return master.promise();
		}
	});
var rsingleTag = (/^<([\w-]+)\s*\/?>(?:<\/\1>|)$/);

var rneedsContext = jTeddy.expr.match.needsContext;



	var risSimple = /^.[^:#\[\.,]*$/;

// Implement the identical functionality for filter and not
	function winnow(elements, qualifier, not) {
		if (jTeddy.isFunction(qualifier)) {
			return jTeddy.grep(elements, function (elem, i) {
				/* jshint -W018 */
				return !!qualifier.call(elem, i, elem) !== not;
			});

		}

		if (qualifier.nodeType) {
			return jTeddy.grep(elements, function (elem) {
				return ( elem === qualifier ) !== not;
			});

		}

		if (typeof qualifier === "string") {
			if (risSimple.test(qualifier)) {
				return jTeddy.filter(qualifier, elements, not);
			}

			qualifier = jTeddy.filter(qualifier, elements);
		}

		return jTeddy.grep(elements, function (elem) {
			return ( indexOf.call(qualifier, elem) > -1 ) !== not;
		});
	}

	jTeddy.filter = function (expr, elems, not) {
		var elem = elems[0];

		if (not) {
			expr = ":not(" + expr + ")";
		}

		return elems.length === 1 && elem.nodeType === 1 ?
			jTeddy.find.matchesSelector(elem, expr) ? [elem] : [] :
			jTeddy.find.matches(expr, jTeddy.grep(elems, function (elem) {
				return elem.nodeType === 1;
			}));
	};

	jTeddy.fn.extend({
		find: function (selector) {
			var i,
				len = this.length,
				ret = [],
				self = this;

			if (typeof selector !== "string") {
				return this.pushStack(jTeddy(selector).filter(function () {
					for (i = 0; i < len; i++) {
						if (jTeddy.contains(self[i], this)) {
							return true;
						}
					}
				}));
			}

			for (i = 0; i < len; i++) {
				jTeddy.find(selector, self[i], ret);
			}

			return this.pushStack(len > 1 ? jTeddy.unique(ret) : ret);
		},
		filter: function (selector) {
			return this.pushStack(winnow(this, selector || [], false));
		},
		not: function (selector) {
			return this.pushStack(winnow(this, selector || [], true));
		},
		is: function (selector) {
			return !!winnow(
				this,

				// If this is a positional/relative selector, check membership in the returned set
				// so $("p:first").is("p:last") won't return true for a doc with two "p".
				typeof selector === "string" && rneedsContext.test(selector) ?
					jTeddy(selector) :
				selector || [],
				false
			).length;
		}
	});


// Initialize a jTeddy object


// A central reference to the root jTeddy(document)
	var rootjTeddy,

	// A simple way to check for HTML strings
	// Prioritize #id over <tag> to avoid XSS via location.hash (#9521)
	// Strict HTML recognition (#11290: must start with <)
	// Shortcut simple #id case for speed
		rquickExpr = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]+))$/,

		init = jTeddy.fn.init = function (selector, context) {
			var match, elem;

			// HANDLE: $(""), $(null), $(undefined), $(false)
			if (!selector) {
				return this;
			}

			// Handle HTML strings
			if (typeof selector === "string") {
				if (selector[0] === "<" &&
					selector[selector.length - 1] === ">" &&
					selector.length >= 3) {

					// Assume that strings that start and end with <> are HTML and skip the regex check
					match = [null, selector, null];

				} else {
					match = rquickExpr.exec(selector);
				}

				// Match html or make sure no context is specified for #id
				if (match && (match[1] || !context)) {

					// HANDLE: $(html) -> $(array)
					if (match[1]) {
						context = context instanceof jTeddy ? context[0] : context;

						// Option to run scripts is true for back-compat
						// Intentionally let the error be thrown if parseHTML is not present
						jTeddy.merge(this, jTeddy.parseHTML(
							match[1],
							context && context.nodeType ? context.ownerDocument || context : document,
							true
						));

						// HANDLE: $(html, props)
						if (rsingleTag.test(match[1]) && jTeddy.isPlainObject(context)) {
							for (match in context) {
								// Properties of context are called as methods if possible
								if (jTeddy.isFunction(this[match])) {
									this[match](context[match]);

									// ...and otherwise set as attributes
								} else {
									this.attr(match, context[match]);
								}
							}
						}

						return this;

						// HANDLE: $(#id)
					} else {
						elem = document.getElementById(match[2]);

						if (elem) {
							// Inject the element directly into the jTeddy object
							this[0] = elem;
							this.length = 1;
						}
						return this;
					}

					// HANDLE: $(expr, $(...))
				} else if (!context || context.jteddy) {
					return ( context || rootjTeddy ).find(selector);

					// HANDLE: $(expr, context)
					// (which is just equivalent to: $(context).find(expr)
				} else {
					return this.constructor(context).find(selector);
				}

				// HANDLE: $(DOMElement)
			} else if (selector.nodeType) {
				this[0] = selector;
				this.length = 1;
				return this;

				// HANDLE: $(function)
				// Shortcut for document ready
			} else if (jTeddy.isFunction(selector)) {
				return rootjTeddy.ready !== undefined ?
					rootjTeddy.ready(selector) :
					// Execute immediately if ready is not present
					selector(jTeddy);
			}

			return jTeddy.makeArray(selector, this);
		};

// Give the init function the jTeddy prototype for later instantiation
	init.prototype = jTeddy.fn;

// Initialize central reference
	rootjTeddy = jTeddy(document);


// The deferred used on DOM ready
	var readyList;

	jTeddy.fn.ready = function (fn) {
		// Add the callback
		jTeddy.ready.promise().done(fn);

		return this;
	};

	jTeddy.extend({
		// Is the DOM ready to be used? Set to true once it occurs.
		isReady: false,

		// A counter to track how many items to wait for before
		// the ready event fires. See #6781
		readyWait: 1,

		// Hold (or release) the ready event
		holdReady: function (hold) {
			if (hold) {
				jTeddy.readyWait++;
			} else {
				jTeddy.ready(true);
			}
		},

		// Handle when the DOM is ready
		ready: function (wait) {

			// Abort if there are pending holds or we're already ready
			if (wait === true ? --jTeddy.readyWait : jTeddy.isReady) {
				return;
			}

			// Remember that the DOM is ready
			jTeddy.isReady = true;

			// If a normal DOM Ready event fired, decrement, and wait if need be
			if (wait !== true && --jTeddy.readyWait > 0) {
				return;
			}

			// If there are functions bound, to execute
			readyList.resolveWith(document, [jTeddy]);

			// Trigger any bound ready events
			if (jTeddy.fn.triggerHandler) {
				jTeddy(document).triggerHandler("ready");
				jTeddy(document).off("ready");
			}
		}
	});

	/**
	 * The ready event handler and self cleanup method
	 */
	function completed() {
		document.removeEventListener("DOMContentLoaded", completed, false);
		window.removeEventListener("load", completed, false);
		jTeddy.ready();
	}

	jTeddy.ready.promise = function (obj) {
		if (!readyList) {

			readyList = jTeddy.Deferred();

			// Catch cases where $(document).ready() is called
			// after the browser event has already occurred.
			// We once tried to use readyState "interactive" here,
			// but it caused issues like the one
			// discovered by ChrisS here: http://bugs.jTeddy.com/ticket/12282#comment:15
			if (document.readyState === "complete") {
				// Handle it asynchronously to allow scripts the opportunity to delay ready
				setTimeout(jTeddy.ready);

			} else {

				// Use the handy event callback
				document.addEventListener("DOMContentLoaded", completed, false);

				// A fallback to window.onload, that will always work
				window.addEventListener("load", completed, false);
			}
		}
		return readyList.promise(obj);
	};

// Kick off the DOM ready check even if the user does not
	jTeddy.ready.promise();



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


	var escapeable = /["\\\x00-\x1f\x7f-\x9f]/g;
	var meta = {    // table of character substitutions
		'\b': '\\b',
		'\t': '\\t',
		'\n': '\\n',
		'\f': '\\f',
		'\r': '\\r',
		'"': '\\"',
		'\\': '\\\\'
	}

	 // JSON RegExp
	var rvalidchars = /^[\],:{}\s]*$/,
		rvalidbraces = /(?:^|:|,)(?:\s*\[)+/g,
		rvalidescape = /\\(?:["\\\/bfnrt]|u[\da-fA-F]{4})/g,
		rvalidtokens = /"[^"\\\r\n]*"|true|false|null|-?(?:\d+\.|)\d+(?:[eE][+-]?\d+|)/g;

	jTeddy.quoteString = function (string)
		// Places quotes around a string, inteligently.
		// If the string contains no control characters, no quote characters, and no
		// backslash characters, then we can safely slap some quotes around it.
		// Otherwise we must also replace the offending characters with safe escape
		// sequences.
	{
		if (escapeable.test(string)) {
			return '"' + string.replace(escapeable, function (a) {
					var c = meta[a];
					if (typeof c === 'string') {
						return c;
					}
					c = a.charCodeAt();
					return '\\u00' + Math.floor(c / 16).toString(16) + (c % 16).toString(16);
				}) + '"'
		}
		return '"' + string + '"';
	}

	jTeddy.toJSON = function (o, compact) {
		var type = typeof(o);

		if (type == "undefined")
			return "undefined";
		else if (type == "number" || type == "boolean")
			return o + "";
		else if (o === null)
			return "null";

		// Is it a string?
		if (type == "string") {
			return jTeddy.quoteString(o);
		}

		// Does it have a .toJSON function?
		if (type == "object" && typeof o.toJSON == "function")
			return o.toJSON(compact);

		// Is it an array?
		if (type != "function" && typeof(o.length) == "number") {
			var ret = [];
			for (var i = 0; i < o.length; i++) {
				ret.push(jTeddy.toJSON(o[i], compact));
			}
			if (compact)
				return "[" + ret.join(",") + "]";
			else
				return "[" + ret.join(", ") + "]";
		}

		// If it's a function, we have to warn somebody!
		if (type == "function") {
			throw new TypeError("Unable to convert object of type 'function' to json.");
		}

		// It's probably an object, then.
		ret = [];
		for (var k in o) {
			var name;
			var type = typeof(k);

			if (type == "number")
				name = '"' + k + '"';
			else if (type == "string")
				name = jTeddy.quoteString(k);
			else
				continue;  //skip non-string or number keys

			val = jTeddy.toJSON(o[k], compact);
			if (typeof(val) != "string") {
				// skip non-serializable values
				continue;
			}

			if (compact)
				ret.push(name + ":" + val);
			else
				ret.push(name + ": " + val);
		}
		return "{" + ret.join(", ") + "}";
	}
	jTeddy.compactJSON = function (o) {
		return jTeddy.toJSON(o, true);
	}

	jTeddy.evalJSON = function (src)
		// Evals JSON that we know to be safe.
	{
		return eval("(" + src + ")");
	}

	jTeddy.secureEvalJSON = function (src)
		// Evals JSON in a way that is *more* secure.
	{
		var filtered = src;
		filtered = filtered.replace(/\\["\\\/bfnrtu]/g, '@');
		filtered = filtered.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']');
		filtered = filtered.replace(/(?:^|:|,)(?:\s*\[)+/g, '');

		if (/^[\],:{}\s]*$/.test(filtered))
			return eval("(" + src + ")");
		else
			throw new SyntaxError("Error parsing JSON, source is not valid.");
	}

	jTeddy.parseJSON = function( data ) {
		// Attempt to parse using the native JSON parser first
		if ( window.JSON && window.JSON.parse ) {
			return window.JSON.parse( data );
		}

		if ( data === null ) {
			return data;
		}

		if ( typeof data === "string" ) {

			// Make sure leading/trailing whitespace is removed (IE can't handle it)
			data = jTeddy.trim( data );

			if ( data ) {
				// Make sure the incoming data is actual JSON
				// Logic borrowed from http://json.org/json2.js
				if ( rvalidchars.test( data.replace( rvalidescape, "@" )
						.replace( rvalidtokens, "]" )
						.replace( rvalidbraces, "")) ) {

					return ( new Function( "return " + data ) )();
				}
			}
		}

		jTeddy.error( "Invalid JSON: " + data );
	}


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



// Register as a named AMD module, since jTeddy can be concatenated with other
// files that may use define, but not via a proper concatenation script that
// understands anonymous AMD modules. A named AMD is safest and most robust
// way to register. Lowercase jTeddy is used because AMD module names are
// derived from file names, and jTeddy is normally delivered in a lowercase
// file name. Do this after creating the global so that if an AMD module wants
// to call noConflict to hide this version of jTeddy, it will work.

// Note that for maximum portability, libraries that are not jTeddy should
// declare themselves as anonymous modules, and avoid setting a global if an
// AMD loader is present. jTeddy is a special case. For more information, see
// https://github.com/jrburke/requirejs/wiki/Updating-existing-libraries#wiki-anon

	if (typeof define === "function" && define.amd) {
		define("jteddy", [], function () {
			return jTeddy;
		});
	}



var
// Map over jTeddy in case of overwrite
	_jTeddy = window.jTeddy,

// Map over the $ in case of overwrite
	_$T = window.$T;

jTeddy.noConflict = function (deep) {
	if (window.$T === jTeddy) {
		window.$T = _$T;
	}

	if (deep && window.jTeddy === jTeddy) {
		window.jTeddy = _jTeddy;
	}

	return jTeddy;
};

// Expose jTeddy and $T identifiers, even in AMD
// (#7102#comment:10, https://github.com/jTeddy/jTeddy/pull/557)
// and CommonJS for browser emulators (#13566)
if (!noGlobal) {
	window.jTeddy = window.$T = jTeddy;
}

return jTeddy;
}))
;
