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
