/* Handlebars Helpers - Dan Harper (http://github.com/danharper) */


/*
 * 计算离左侧的宽度距离
 */

Handlebars.registerHelper('mxgifWidth', function(value, fn) {
    var _width = "width-2";
    for (var i = 0; i < value.length; i++) {
        if(value[i]["isNodeName"]=="1"){
            if(value[i].item>=0){
                _width = "width-1";
            }
        }
    }
    return _width;
});



/*
 * 计算子元素的高度类型，分1，2，3种类型
 */
Handlebars.registerHelper('mxgifHeight1', function(value, fn) {
    var _height = "height-1";
    if(value.length>5){
         _height = "height-2";
    }
    if(value.length>10){
         _height = "height-3";
    }
    for (var i = 0; i < value.length; i++) {
        if(value[i]["isNodeName"]=="1"){
            if(value[i].item>=0){
                _height = "";
            }
        }
    }
    return _height;
});


/*
 * 计算子元素的高度类型，分1，2种类型
 */
Handlebars.registerHelper('mxgifHeight2', function(value, fn) {
    var _height = "height-2";
    if(value.length>5){
         _height = "height-3";
    }
    for (var i = 0; i < value.length; i++) {
        if(value[i]["isNodeName"]=="1"){
            if(value[i].item>=0){
                _height = "";
            }
        }
    }
    return _height;
});



/*
 * 计算子itme元素的个数
 */
Handlebars.registerHelper('mxgifLength', function(value, fn) {
    var _length = 0;
    if(value.length>=5){
         _length = "item-5";
    }else{
         _length = "item-"+value.length;
    }
    for (var i = 0; i < value.length; i++) {
        if(value[i]["isNodeName"]=="1"){
            if(value[i].item>=0){
                _length = "";
            }
        }
    }
    return _length;
});


/*
 * 判断是否为节点展示
 */
Handlebars.registerHelper("isNodeNameCompare",function(v1,v2,options){
    if(parseInt(v1)==v2){
        //满足添加继续执行
        return options.fn(this);
      }else{
       //不满足条件执行{{else}}部分
        return options.inverse(this);
      }
});




/*
 * 根据状态码获取员工的状态值
 */
Handlebars.registerHelper('getUserState', function(value, fn) {
    if (value == "1") {
        return "有效";
    }
    if (value == "0") {
        return "失效";
    }
});
Handlebars.registerHelper('getYorN', function(value, fn) {
    if (value == "Y") {
        return "是";
    }
    if (value == "N") {
        return "否";
    }
});
/*
 * 根据状态码获取任务执行结果
 */
Handlebars.registerHelper('getResultType', function(value, fn) {
    if (value == "0") {
        return "成功";
    }
    if (value == "1") {
        return "失败";
    }
    if (value == "2") {
        return "未执行";
    }
    if (value == "3") {
        return "中断";
    }
});
/*
 * 根据状态码获取任务执行状态
 */
Handlebars.registerHelper('getTaskType', function(value, fn) {
    if (value == "1") {
        return "未执行";
    }
    if (value == "2") {
        return "执行中";
    }
    if (value == "3") {
        return "执行完成";
    }
    if (value == "4") {
        return "执行失败";
    }
});

/*
 * 根据是否成功让input框变化
 */
Handlebars.registerHelper('checkedState', function(value, fn) {
    if (value == "Y") {
        return "class=hide";
    }
    if (value == "N") {
        return "";
    }
});
Handlebars.registerHelper('remainDaysBla', function(value, days, fn) {
    if (value == "true") {
        if (days != null && days != undefined && days != "") {
            return "还剩";
        } else {
            return "";
        }

    }
});

Handlebars.registerHelper('remainDaysRed', function(value, days, fn) {
    if (value == "true") {
        if (days != null && days != undefined && days != "") {
            return days;
        } else {
            return "";
        }

    }
});
Handlebars.registerHelper('remainDaysStop', function(value, days, fn) {
    if (value == "true") {

    } else {
        return "欠费停机";
    }
});

Handlebars.registerHelper('remainDaysBla1', function(value, days, fn) {
    if (value == "true") {
        if (days != null && days != undefined && days != "") {
            return "天到期";
        } else {
            return "";
        }

    }
});

/**
 * If Equals if_eq this compare=that
 */
Handlebars.registerHelper('if_eq', function(context, options) {
    if (context == options.hash.compare)
        return options.fn(this);
    return options.inverse(this);
});

/**
 * Unless Equals unless_eq this compare=that
 */
Handlebars.registerHelper('unless_eq', function(context, options) {
    if (context == options.hash.compare)
        return options.inverse(this);
    return options.fn(this);
});

/**
 * If Greater Than if_gt this compare=that
 */
Handlebars.registerHelper('if_gt', function(context, options) {
    if (context > options.hash.compare)
        return options.fn(this);
    return options.inverse(this);
});

/**
 * Unless Greater Than unless_gt this compare=that
 */
Handlebars.registerHelper('unless_gt', function(context, options) {
    if (context > options.hash.compare)
        return options.inverse(this);
    return options.fn(this);
});

/**
 * If Less Than if_lt this compare=that
 */
Handlebars.registerHelper('if_lt', function(context, options) {
    if (context < options.hash.compare)
        return options.fn(this);
    return options.inverse(this);
});

/**
 * Unless Less Than unless_lt this compare=that
 */
Handlebars.registerHelper('unless_lt', function(context, options) {
    if (context < options.hash.compare)
        return options.inverse(this);
    return options.fn(this);
});

/**
 * If Greater Than or Equal To if_gteq this compare=that
 */
Handlebars.registerHelper('if_gteq', function(context, options) {
    if (context >= options.hash.compare)
        return options.fn(this);
    return options.inverse(this);
});

/**
 * Unless Greater Than or Equal To unless_gteq this compare=that
 */
Handlebars.registerHelper('unless_gteq', function(context, options) {
    if (context >= options.hash.compare)
        return options.inverse(this);
    return options.fn(this);
});

/**
 * If Less Than or Equal To if_lteq this compare=that
 */
Handlebars.registerHelper('if_lteq', function(context, options) {
    if (context <= options.hash.compare)
        return options.fn(this);
    return options.inverse(this);
});

/**
 * Unless Less Than or Equal To unless_lteq this compare=that
 */
Handlebars.registerHelper('unless_lteq', function(context, options) {
    if (context <= options.hash.compare)
        return options.inverse(this);
    return options.fn(this);
});

/**
 * 带序号的循环 {{index}}为序号。 eg ： {{#each_with_index array}} {{index}}
 * {{/each_with_index}}
 */
Handlebars.registerHelper("each_with_index", function(array, fn) {
    var buffer = "";
    array = array ? array : [''];
    for (var i = 0, j = array.length; i < j; i++) {
        var item = array[i];

        // stick an index property onto the item, starting with 1, may make
        // configurable later
        item.index = i + 1;

        // show the inside of the block
        buffer += fn(item);
    }
    // return the finished buffer
    return buffer;
});

/**
 * Check current index is odd or even 判断当前行的奇偶
 */
Handlebars.registerHelper("isOdd", function(index, options) {
    var str = (index % 2 == 0) ? 'even' : 'odd';
    return new Handlebars.SafeString(str);
});

/**
 * 裁剪内容,对于过长的string,使用...来替代 参数：内容,保留长度
 */
Handlebars.registerHelper("shrink", function(content, length) {
    content = content ? content : '';
    if (content.length > length) {
        content = content.slice(0, length);
        content += '...';
    }
    return new Handlebars.SafeString(content);
});

/**
 * 裁剪内容,对于过长的string,使用...来替代 参数：内容,保留长度
 */
Handlebars.registerHelper("shrinkHtmlFilter", function(content, length) {
    content = content ? content : '';
    if (content.length > length) {
        content = content.slice(0, length);
        content += '...';
    }
    return new Handlebars.SafeString(Rose.htmlFilter(content,3));
});

Handlebars.registerHelper("subStr", function(content, start, length) {
    content = content ? content : '';
    if (content.length > length && content.length > start) {
        content = content.slice(start, length);
    }
    return new Handlebars.SafeString(content);
});

Handlebars.registerHelper("ternary", function(content, value) {
    content = content ? content : value;
    return new Handlebars.SafeString(content);
});

/**
 * SafeString
 */
Handlebars.registerHelper('safeString', function(text, options) {
    text = text ? text : '';
    return new Handlebars.SafeString(text);
});

Handlebars.registerHelper('add', function(value, addition) {
    return value + addition;
});
Handlebars.registerHelper('subtract', function(value, substraction) {
    return value - substraction;
});
Handlebars.registerHelper('divide', function(value, divisor) {
    return value / divisor;
});
Handlebars.registerHelper('multiply', function(value, multiplier) {
    return value * multiplier;
});
Handlebars.registerHelper('floor', function(value) {
    return Math.floor(value);
});
Handlebars.registerHelper('ceil', function(value) {
    return Math.ceil(value);
});
Handlebars.registerHelper('round', function(value) {
    return Math.round(value);
});

/**
 * 保留几位小数 value：数值 decimalNu：保留的小数个数
 */
Handlebars.registerHelper('toFixed', function(value, decimalNum) {
    value = Number(value);
    decimalNum = decimalNum || 2;
    return value.toFixed(decimalNum);
});
/**
 * 计算执行成功率
 */
Handlebars.registerHelper("getSuccessRate", function(successCase, totalCase, fn) {
    var successRate = Math.round(parseInt(successCase) / parseInt(totalCase)*10000)/100.00 + '%';
	return successRate;
});
/**
 * 计算执行时间
 */
Handlebars.registerHelper("getSpendTime", function(spendTime, fn) {
    var _spendTime = Math.floor(spendTime/1440) + "天" + Math.floor(spendTime/60) + "小时" + (spendTime%60) + "分";
	return _spendTime;
});

