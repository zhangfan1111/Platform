define([
	"./var/arr",
	"./var/concat",
	"./var/indexOf",
	"./var/push",
	"./var/slice",
	"./var/class2type",
	"./var/toString",
	"./var/hasOwn",
	"./var/support",
	"./var/document"
], function (arr, concat, indexOf, push, slice, class2type, toString, hasOwn, support, document) {
	//var version = "@VERSION",
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

	return jTeddy;
});
