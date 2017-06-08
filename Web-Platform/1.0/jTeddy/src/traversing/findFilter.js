define([
	"../core",
	"../var/indexOf",
	"./var/rneedsContext",
	"../selector"
], function (jTeddy, indexOf, rneedsContext) {

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

});
