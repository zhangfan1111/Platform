// Initialize a jTeddy object
define([
	"../core",
	"../var/document",
	"./var/rsingleTag",
	"../traversing/findFilter"
], function (jTeddy, document, rsingleTag) {

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

	return init;

});
