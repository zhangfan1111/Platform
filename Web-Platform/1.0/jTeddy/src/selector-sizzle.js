define([
	"./core",
	"sizzle"
], function (jTeddy, Sizzle) {

	jTeddy.find = Sizzle;
	jTeddy.expr = Sizzle.selectors;
	jTeddy.expr[":"] = jTeddy.expr.pseudos;
	jTeddy.unique = Sizzle.uniqueSort;
	jTeddy.text = Sizzle.getText;
	jTeddy.isXMLDoc = Sizzle.isXML;
	jTeddy.contains = Sizzle.contains;

});
