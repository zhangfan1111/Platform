define([
	"./core",
	"./selector",
	"./callbacks",
	"./deferred",
	"./core/ready",
	"./arrayPro",
	"./date",
	"./hashMap",
	"./json",
	"./string",
	"./exports/amd"
], function (jTeddy) {

	return (window.jTeddy = window.$T = jTeddy);

});
