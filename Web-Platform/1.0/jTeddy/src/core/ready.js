define([
	"../core",
	"../var/document",
	"../core/init",
	"../deferred"
], function (jTeddy, document) {

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

});
