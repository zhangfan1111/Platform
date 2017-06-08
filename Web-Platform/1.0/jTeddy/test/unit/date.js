module("Module Date");

test("基本验证", function () {
	expect(21);

	ok(Date.CS.Y, "Date.CS.Y");
	ok(Date.CS.M, "Date.CS.M");
	ok(Date.CS.D, "Date.CS.D");
	ok(Date.CS.LH, "Date.CS.LH");
	ok(Date.CS.H, "Date.CS.H");
	ok(Date.CS.LM, "Date.CS.LM");
	ok(Date.CS.S, "Date.CS.S");
	ok(Date.CS.MS, "Date.CS.MS");
	ok(Date.CS.SD, "Date.CS.SD");
	ok(Date.CS.PD, "Date.CS.PD");
	ok(Date.CS.GD, "Date.CS.GD");
	ok(Date.CS.YD, "Date.CS.YD");

	ok(Date.fn.difference, "Date.fn.difference()");
	ok(Date.fn.between, "Date.fn.between()");
	ok(Date.fn.addDate, "Date.fn.addDate()");
	ok(Date.fn.isEqual, "Date.fn.isEqual()");
	ok(Date.fn.isLeapYear, "Date.fn.isLeapYear()");
	ok(Date.fn.getYDays, "Date.fn.getYDays()");
	ok(Date.fn.getMDays, "Date.fn.getMDays()");
	ok(Date.fn.formatMill, "Date.fn.formatMill()");

	ok(Date.prototype.pattern, "Date.prototype.pattern()");
});

test("Date.CS", function () {
	expect(12);

	equal(Date.CS.Y, 'yyyy', "Date.CS.Y == 'yyyy'");
	equal(Date.CS.M, 'MM', "Date.CS.M == 'MM'");
	equal(Date.CS.D, 'dd', "Date.CS.D == 'dd'");
	equal(Date.CS.LH, 'hh', "Date.CS.LH == 'hh'");
	equal(Date.CS.H, 'HH', "Date.CS.H == 'HH'");
	equal(Date.CS.LM, 'mm', "Date.CS.LM == 'mm'");
	equal(Date.CS.S, 'ss', "Date.CS.S == 'ss'");
	equal(Date.CS.MS, 'S', "Date.CS.MS == 'S'");
	equal(Date.CS.SD, 'yyyy-MM-dd hh:mm:ss.S', "Date.CS.SD == 'yyyy-MM-dd hh:mm:ss.S'");
	equal(Date.CS.PD, 'yyyy-M-d h:m:s.S', "Date.CS.PD == 'yyyy-M-d h:m:s.S'");
	equal(Date.CS.GD, 'yyyy-MM-dd hh:mm:ss', "Date.CS.GD == 'yyyy-MM-dd hh:mm:ss'");
	equal(Date.CS.YD, 'yyyy-MM-dd', "Date.CS.YD == 'yyyy-MM-dd'");
});

test("pattern()", function () {
	expect(4);

	var dateStr = (new Date()).pattern(Date.CS.SD);

	ok(dateStr, "the date is " + dateStr);

	dateStr = (new Date()).pattern(Date.CS.PD);
	ok(dateStr, "the date is " + dateStr);

	dateStr = (new Date()).pattern(Date.CS.GD);
	ok(dateStr, "the date is " + dateStr);

	dateStr = (new Date()).pattern(Date.CS.YD);
	ok(dateStr, "the date is " + dateStr);
});

test("fn.difference()", function () {
	expect(33);

	var sDate = "2014-10-07";
	var eDate = "2014-11-07";

	notEqual(Date.fn.difference('d', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('d', sDate, eDate), 31, "the result is " + 31);

	notEqual(Date.fn.difference('h', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('h', sDate, eDate), 31 * 24, "the result is " + 31 * 24);

	notEqual(Date.fn.difference('m', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('m', sDate, eDate), 31 * 24 * 60, "the result is " + 31 * 24 * 60);

	notEqual(Date.fn.difference('s', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('s', sDate, eDate), 31 * 24 * 60 * 60, "the result is " + 31 * 24 * 60 * 60);

	equal(Date.fn.difference('T', sDate, eDate), 31 * 1000 * 24 * 60 * 60, "the result is " + 31 * 1000 * 24 * 60 * 60);

	sDate = "2014-10-07 10:30";
	eDate = "2014-11-07 10:30";

	notEqual(Date.fn.difference('d', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('d', sDate, eDate), 31, "the result is " + 31);

	notEqual(Date.fn.difference('h', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('h', sDate, eDate), 31 * 24, "the result is " + 31 * 24);

	notEqual(Date.fn.difference('m', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('m', sDate, eDate), 31 * 24 * 60, "the result is " + 31 * 24 * 60);

	notEqual(Date.fn.difference('s', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('s', sDate, eDate), 31 * 24 * 60 * 60, "the result is " + 31 * 24 * 60 * 60);

	equal(Date.fn.difference('T', sDate, eDate), 31 * 1000 * 24 * 60 * 60, "the result is " + 31 * 1000 * 24 * 60 * 60);

	sDate = "2014-10-07 10:30:40";
	eDate = "2014-11-07 10:30:40";

	notEqual(Date.fn.difference('d', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('d', sDate, eDate), 31, "the result is " + 31);

	notEqual(Date.fn.difference('h', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('h', sDate, eDate), 31 * 24, "the result is " + 31 * 24);

	notEqual(Date.fn.difference('m', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('m', sDate, eDate), 31 * 24 * 60, "the result is " + 31 * 24 * 60);

	notEqual(Date.fn.difference('s', sDate, eDate), 31 * 1000 * 60 * 60 * 24, "the result is not " + 31 * 1000 * 60 * 60 * 24);
	equal(Date.fn.difference('s', sDate, eDate), 31 * 24 * 60 * 60, "the result is " + 31 * 24 * 60 * 60);

	equal(Date.fn.difference('T', sDate, eDate), 31 * 1000 * 24 * 60 * 60, "the result is " + 31 * 1000 * 24 * 60 * 60);

	sDate = "2014-10-07 10:30:40.100";
	eDate = "2014-11-07 10:30:40.100";

	notEqual(Date.fn.difference('T', sDate, eDate), 31 * 1000 * 24 * 60 * 60, "the result is 'NaN'");
	equal(Date.fn.difference('T', sDate, eDate), 'NaN', "the result is 'NaN'");

	sDate = "2014-10-07 10";
	eDate = "2014-11-07 10";

	notEqual(Date.fn.difference('T', sDate, eDate), 31 * 1000 * 24 * 60 * 60, "the result is 'NaN'");
	equal(Date.fn.difference('T', sDate, eDate), 'NaN', "the result is 'NaN'");

	sDate = "2014/10/07 10:30:40";
	eDate = "2014/11/07 10:30:40";

	equal(Date.fn.difference('T', sDate, eDate), 31 * 1000 * 24 * 60 * 60, "the result is " + 31 * 1000 * 24 * 60 * 60);
	notEqual(Date.fn.difference('T', sDate, eDate), 'NaN', "the result is " + 31 * 1000 * 24 * 60 * 60);
});

test("fn.between()", function () {
	expect(5);

	var tdate = new Date();
	var sDate = new Date();
	var eDate = new Date();

	tdate.setTime(new Date().getTime() - 10000);
	sDate.setTime(new Date().getTime() - 20000);
	deepEqual(Date.fn.between(tdate, sDate, eDate), true, " tDate is between sDate and eDate");

	tdate.setTime(eDate.getTime());
	deepEqual(Date.fn.between(tdate, sDate, eDate), true, " tDate is between sDate and eDate, tDate == eDate");

	tdate.setTime(new Date().getTime() - 20000);
	deepEqual(Date.fn.between(tdate, sDate, eDate), true, " tDate is between sDate and eDate, tDate == sDate");

	tdate.setTime(new Date().getTime() - 30000);
	deepEqual(Date.fn.between(tdate, sDate, eDate), false, " tDate is not between sDate and eDate, tDate < sDate");

	tdate = "2014-10-07 10:30:40";
	sDate = "2014-10-17 10:30:40";
	eDate = "2014-11-07 10:30:40";
	throws(function () {
		Date.fn.between(tdate, sDate, eDate);
	}, null, "the params type is error, the type should be Date");
});

test("fn.addDate()", function () {
	expect(4);

	var date = new Date();
	date.setTime(Date.parse("2014-10-07 10:30:40".replace(/-/g, "/")));

	deepEqual(Date.fn.addDate(date, 1000).getTime(), (new Date()).setTime(Date.parse("2014-10-07 10:30:41".replace(/-/g, "/"))),
		"the result is '2014-10-07 10:30:41'");
	deepEqual(Date.fn.addDate(date, '1000').getTime(), NaN, "the result is NaN, the arg[1]'s type is wrong, '1000' should be 1000");

	date.setTime(Date.parse("2014-10-07 10:30:40".replace(/-/g, "/")));
	deepEqual(Date.fn.addDate(date, -1000).getTime(), (new Date()).setTime(Date.parse("2014-10-07 10:30:39".replace(/-/g, "/"))),
		"the result is '2014-10-07 10:30:39'");

	date.setTime(Date.parse("2014-10-07 10:30:40".replace(/-/g, "/")));
	deepEqual(Date.fn.addDate(date, 0).getTime(), (new Date()).setTime(Date.parse("2014-10-07 10:30:40".replace(/-/g, "/"))),
		"the result is '2014-10-07 10:30:40'");
});

test("fn.isEqual()", function () {
	expect(4);

	var sDate = new Date();
	var eDate = new Date();

	equal(Date.fn.isEqual(sDate, eDate), 0, "sDate == eDate, the result is 0");

	sDate.setTime(new Date().getTime() - 20000);
	equal(Date.fn.isEqual(sDate, eDate), -1, "sDate < eDate, the result is -1");

	eDate.setTime(new Date().getTime() - 40000);
	equal(Date.fn.isEqual(sDate, eDate), 1, "sDate > eDate, the result is 1");

	sDate = "2014-10-17 10:30:40";
	eDate = "2014-11-07 10:30:40";
	throws(function () {
		Date.fn.isEqual(tdate, sDate, eDate);
	}, null, "the params type is error, the type should be Date");
});

test("fn.isLeapYear()", function () {
	expect(3);

	var year = 2015;

	equal(Date.fn.isLeapYear(year), false, "2015 is not leap year");
	equal(Date.fn.isLeapYear(year + ''), false, "2015 is not leap year");

	equal(Date.fn.isLeapYear(2012), true, "2012 is leap year");
});

test("fn.getYDays()", function () {
	expect(3);

	var year = 2015;

	equal(Date.fn.getYDays(year), 365, "2015 is not leap year");
	equal(Date.fn.getYDays(year + ''), 365, "2015 is not leap year");

	equal(Date.fn.getYDays(2012), 366, "2012 is leap year");
});

test("fn.getMDays()", function () {
	expect(6);

	equal(Date.fn.getMDays(4, 2015), 30, "the April has 30 days");
	equal(Date.fn.getMDays(2, 2015), 28, "2015 is not leap year, the February has 28 days");
	equal(Date.fn.getMDays(1, 2015), 31, "the January has 31 days");

	equal(Date.fn.getMDays(13, 2015), 0, "the result is 0, the month rang is 1~12, but your value is 13");
	equal(Date.fn.getMDays(0, 2015), 0, "the result is 0, the month rang is 1~12, but your value is 0");

	equal(Date.fn.getMDays(2, 2012), 29, "2012 is leap year, the February has 29 days");
});

test("fn.formatMill()", function () {
	expect(3);

	var num = 1412649041000;
	var obj = {
		y: 2014,
		m: 10,
		d: 7,
		h: 10,
		mi: 30,
		s: 41,
		ms: 0
	};

	deepEqual(Date.fn.formatMill(num), obj, " the result is {	y: 2014,	m: 10,	d: 7,	h: 10,	mi: 30, s: 41,	ms: 0}");
	deepEqual(Date.fn.formatMill(num + ''), {}, " the result is {}, the param's type should be number");
	deepEqual(Date.fn.formatMill(-100), {}, " the result is {}, the param's type should be > 0");
});
