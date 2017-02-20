/* Handlebars Helpers - Dan Harper (http://github.com/danharper) */

/**
 * 自定义方法：跳转地址 eg ： href="{{isFolder1 adPic colorID modelID brandID}}"
 */

// 电视新装费用
Handlebars
		.registerHelper(
				"tvFeeInfo",
				function(json, fn) {
					var len1 = '';
					if (json.length == 0) {
						len1 = '';
					} else {
						for ( var i = 0; i < json.length; i++) {
							var totalMoney = parseInt(json[i]['FACT_MONEY']
									.toString()) / 100;
							len1 += '<div class="dw-fee-cont" style="width:100%" data-DISTCOUNT_MONEY="'
									+ json[i]['DISTCOUNT_MONEY']
									+ '" data-FACT_MONEY="'
									+ json[i]['FACT_MONEY']
									+ '" data-OFFER_ID="'
									+ json[i]['OFFER_ID']
									+ '" data-OFFER_INST_ID="'
									+ json[i]['OFFER_INST_ID']
									+ '" data-PRICE_ITEM_ID="'
									+ json[i]['PRICE_ITEM_ID']
									+ '" data-PRICE_ITEM_NAME="'
									+ json[i]['PRICE_ITEM_NAME']
									+ '" data-PRICE_PLAN_ID="'
									+ json[i]['PRICE_PLAN_ID']
									+ '" data-SHOULD_MONEY="'
									+ json[i]['SHOULD_MONEY']
									+ '"  ><div id="demo-checkboxex" style="width:100%;  display: inline;"></div></div>';
						}
					}
					return new Handlebars.SafeString(len1);
				});

// 电视新装档位加载

Handlebars
		.registerHelper(
				"tvDwInfo",
				function(json, fn) {
					var len1 = '';
					// MK_PROGRAM_ID 营销案ID
					// MK_PROGRAM_KIND_ID;//营销案档次ID
					// MK_PLAN_REL_PROM;//促销ID
					// data-XXXXX="'+json[0]['XXXX']+'"
					if (json.length == 0) {
						len1 = '';
					} else if (json.length == 1) {
						len1 = '<div class="tv-busi-warp active"  data-MK_PROGRAM_ID="'
								+ json[0]['MK_PROGRAM_ID']
								+ '"  data-MK_PROGRAM_KIND_ID="'
								+ json[0]['MK_PROGRAM_KIND_ID']
								+ '"  data-MK_PLAN_REL_PROM="'
								+ json[0]['MK_PLAN_REL_PROM']
								+ '"  data-PLAN_DESC="'
								+ json[0]['PLAN_DESC']
								+ '" data-PLAN_KIND_ID="'
								+ json[0]['PLAN_KIND_ID']
								+ '" data-PLAN_NAME="'
								+ json[0]['PLAN_NAME']
								+ '" data-PLAN_REA_PRICE="'
								+ json[0]['PLAN_REA_PRICE']
								+ '" data-PLAN_REC_PRICE="'
								+ json[0]['PLAN_REC_PRICE']
								+ '" data-PLAN_EFF_TIME="'
								+ json[0]['PLAN_EFF_TIME']
								+ '" data-PLAN_EXP_TIME="'
								+ json[0]['PLAN_EXP_TIME']
								+ '"><img src="res/css/img/check_green.png"><p class="after">'
								+ json[0].PLAN_NAME + ' </p></div>';
					} else if (json.length == 2) {
						len1 = '<div class="tv-busi-warp active" data-MK_PROGRAM_ID="'
								+ json[0]['MK_PROGRAM_ID']
								+ '"  data-MK_PROGRAM_KIND_ID="'
								+ json[0]['MK_PROGRAM_KIND_ID']
								+ '"  data-MK_PLAN_REL_PROM="'
								+ json[0]['MK_PLAN_REL_PROM']
								+ '" data-PLAN_DESC="'
								+ json[0]['PLAN_DESC']
								+ '" data-PLAN_KIND_ID="'
								+ json[0]['PLAN_KIND_ID']
								+ '" data-PLAN_NAME="'
								+ json[0]['PLAN_NAME']
								+ '" data-PLAN_REA_PRICE="'
								+ json[0]['PLAN_REA_PRICE']
								+ '" data-PLAN_REC_PRICE="'
								+ json[0]['PLAN_REC_PRICE']
								+ '" data-PLAN_EFF_TIME="'
								+ json[0]['PLAN_EFF_TIME']
								+ '" data-PLAN_EXP_TIME="'
								+ json[0]['PLAN_EXP_TIME']
								+ '"><img src="res/css/img/check_green.png"><p class="after">'
								+ json[0].PLAN_NAME
								+ '</p></div>'
								+ '<div class="tv-busi-warp " data-MK_PROGRAM_ID="'
								+ json[1]['MK_PROGRAM_ID']
								+ '"  data-MK_PROGRAM_KIND_ID="'
								+ json[1]['MK_PROGRAM_KIND_ID']
								+ '"  data-MK_PLAN_REL_PROM="'
								+ json[1]['MK_PLAN_REL_PROM']
								+ '" data-PLAN_DESC="'
								+ json[1]['PLAN_DESC']
								+ '" data-PLAN_KIND_ID="'
								+ json[1]['PLAN_KIND_ID']
								+ '" data-PLAN_NAME="'
								+ json[1]['PLAN_NAME']
								+ '" data-PLAN_REA_PRICE="'
								+ json[1]['PLAN_REA_PRICE']
								+ '" data-PLAN_REC_PRICE="'
								+ json[1]['PLAN_REC_PRICE']
								+ '" data-PLAN_EFF_TIME="'
								+ json[1]['PLAN_EFF_TIME']
								+ '" data-PLAN_EXP_TIME="'
								+ json[1]['PLAN_EXP_TIME']
								+ '"><img src="res/css/img/check_green.png"><p class="after">'
								+ json[1].PLAN_NAME + ' </p></div>';
					} else {
						len1 = '<div class="tv-busi-warp active" data-MK_PROGRAM_ID="'
								+ json[0]['MK_PROGRAM_ID']
								+ '"  data-MK_PROGRAM_KIND_ID="'
								+ json[0]['MK_PROGRAM_KIND_ID']
								+ '"  data-MK_PLAN_REL_PROM="'
								+ json[0]['MK_PLAN_REL_PROM']
								+ '" data-PLAN_DESC="'
								+ json[0]['PLAN_DESC']
								+ '" data-PLAN_KIND_ID="'
								+ json[0]['PLAN_KIND_ID']
								+ '" data-PLAN_NAME="'
								+ json[0]['PLAN_NAME']
								+ '" data-PLAN_REA_PRICE="'
								+ json[0]['PLAN_REA_PRICE']
								+ '" data-PLAN_REC_PRICE="'
								+ json[0]['PLAN_REC_PRICE']
								+ '" data-PLAN_EFF_TIME="'
								+ json[0]['PLAN_EFF_TIME']
								+ '" data-PLAN_EXP_TIME="'
								+ json[0]['PLAN_EXP_TIME']
								+ '"><img src="res/css/img/check_green.png"><p class="after">'
								+ json[0].PLAN_NAME
								+ '</p></div>'
								+ '<div class="tv-busi-warp " data-MK_PROGRAM_ID="'
								+ json[1]['MK_PROGRAM_ID']
								+ '"  data-MK_PROGRAM_KIND_ID="'
								+ json[1]['MK_PROGRAM_KIND_ID']
								+ '"  data-MK_PLAN_REL_PROM="'
								+ json[1]['MK_PLAN_REL_PROM']
								+ '" data-PLAN_DESC="'
								+ json[1]['PLAN_DESC']
								+ '" data-PLAN_KIND_ID="'
								+ json[1]['PLAN_KIND_ID']
								+ '" data-PLAN_NAME="'
								+ json[1]['PLAN_NAME']
								+ '" data-PLAN_REA_PRICE="'
								+ json[1]['PLAN_REA_PRICE']
								+ '" data-PLAN_REC_PRICE="'
								+ json[1]['PLAN_REC_PRICE']
								+ '" data-PLAN_EFF_TIME="'
								+ json[1]['PLAN_EFF_TIME']
								+ '" data-PLAN_EXP_TIME="'
								+ json[1]['PLAN_EXP_TIME']
								+ '"><img src="res/css/img/check_green.png"><p class="after">'
								+ json[1].PLAN_NAME + ' </p></div>';
						for ( var i = 2; i < json.length; i++) {
							len1 += '<div class="tv-busi-warp hid" data-MK_PROGRAM_ID="'
									+ json[i]['MK_PROGRAM_ID']
									+ '"  data-MK_PROGRAM_KIND_ID="'
									+ json[i]['MK_PROGRAM_KIND_ID']
									+ '"  data-MK_PLAN_REL_PROM="'
									+ json[i]['MK_PLAN_REL_PROM']
									+ '"   data-PLAN_DESC="'
									+ json[i]['PLAN_DESC']
									+ '" data-PLAN_KIND_ID="'
									+ json[i]['PLAN_KIND_ID']
									+ '" data-PLAN_NAME="'
									+ json[i]['PLAN_NAME']
									+ '" data-PLAN_REA_PRICE="'
									+ json[i]['PLAN_REA_PRICE']
									+ '" data-PLAN_REC_PRICE="'
									+ json[i]['PLAN_REC_PRICE']
									+ '" data-PLAN_EFF_TIME="'
									+ json[i]['PLAN_EFF_TIME']
									+ '" data-PLAN_EXP_TIME="'
									+ json[i]['PLAN_EXP_TIME']
									+ '"><img src="res/css/img/check_green.png" ><p class="after">'
									+ json[i].PLAN_NAME + ' </p></div>';
						}
					}
					return new Handlebars.SafeString(len1);
				});

/**
 * 自定义方法：跳转地址 eg ： href="{{isFolder1 adPic colorID modelID brandID}}"
 */
Handlebars
		.registerHelper(
				"isAd",
				function(json, fn) {
					var buffer = '';
					var len1 = '<div class="title-wrap" style="margin:0;color:#333333"><span style="padding: 20px 15px 0 20px;line-height: 13px;font-size:13px;">'
							+ '裸宽带套餐</span><span class="fee_more_span"><a class="fee_more" style="font-size:12px;">更多<img src="res/css/img/more.png" class="img-more"></a></span></div>';
					var len2 = '';
					var str = '';
					var tj_len = 2;
					// json.length=1;
					if (json.length == 0) {
						buffer = '';
					} else if (json.length == 1) {

						len2 = '<div class="my-list-busi">'
								+ '<div class="list-busi" id="J_list">'
								+ '<div class="list-busi-wrap" style="width:98%;" data-marketProgId="'
								+ json[0].marketProgId
								+ '" data-marketKindId="'
								+ json[0].marketKindId
								+ '"  data-months="'
								+ json[0].months
								+ '" data-productId="'
								+ json[0].productId
								+ '" data-privilege="'
								+ json[0]['promotionDesc']
								+ '" data-money="'
								+ json[0].totalMoney
								+ '" data-start="'
								+ json[0].showEffTime
								+ '" data-end="'
								+ json[0].showExpTime
								+ '" data-busi="'
								+ json[0].businessDesc
								+ '" data-detail="'
								+ json[0].totalMoney
								+ '元/'
								+ json[0].businessDesc
								+ '" data-pid="'
								+ json[0].offerVasId
								+ '">'
								+ '<i class="gbtn-icon "></i>'
								+ '<p class="after">'
								+ json[0].totalMoney
								+ '元&nbsp;&nbsp;'
								+ json[0].businessDesc
								+ '</p>'
								+ '<p class="before">原价：<del class="originPrice"><span>'
								+ json[0].oldMoney
								+ '元</span></del></p>'
								+ '</div>' + '</div>' + '</div>';
						buffer = len1 + len2;
					} else if (json.length > 1 && json.length < tj_len) {
						for ( var i = 0; i < json.length; i++) {
							var str1 = '<div class="list-busi-wrap"  data-marketProgId="'
									+ json[i].marketProgId
									+ '" data-marketKindId="'
									+ json[i].marketKindId
									+ '" data-months="'
									+ json[i].months
									+ '" data-productid="'
									+ json[i].productId
									+ '" data-money="'
									+ json[i].totalMoney
									+ '" data-start="'
									+ json[i].showEffTime
									+ '" data-end="'
									+ json[i].showExpTime
									+ '" data-privilege="'
									+ json[i]['promotionDesc']
									+ '" data-busi="'
									+ json[i].businessDesc
									+ '" data-detail="'
									+ json[i].totalMoney
									+ '元/'
									+ json[i].businessDesc
									+ '" data-pid="'
									+ json[i].offerVasId
									+ '">'
									+ '<i class="gbtn-icon"></i>'
									+ '<p class="after">'
									+ json[i].totalMoney
									+ '元&nbsp;&nbsp;'
									+ json[i].businessDesc
									+ '</p>'
									+ '<p class="before">原价：<del class="originPrice"><span>'
									+ json[i].oldMoney
									+ '元</span></del></p>'
									+ '</div>';
							str += str1;
						}

						len2 = '<div class="my-list-busi">'
								+ '<div class="list-busi" id="J_list">' + str
								+ '</div>' + '</div>';
						buffer = len1 + len2;
					} else if (json.length == tj_len || json.length > tj_len) {
						for ( var i = 0; i < tj_len; i++) {
							var str1 = '<div class="list-busi-wrap"  data-marketProgId="'
									+ json[i].marketProgId
									+ '" data-marketKindId="'
									+ json[i].marketKindId
									+ '" data-months="'
									+ json[i].months
									+ '" data-productid="'
									+ json[i].productId
									+ '" data-money="'
									+ json[i].totalMoney
									+ '" data-start="'
									+ json[i].showEffTime
									+ '" data-end="'
									+ json[i].showExpTime
									+ '" data-privilege="'
									+ json[i]['promotionDesc']
									+ '" data-busi="'
									+ json[i].businessDesc
									+ '" data-detail="'
									+ json[i].totalMoney
									+ '元/'
									+ json[i].businessDesc
									+ '" data-pid="'
									+ json[i].offerVasId
									+ '">'
									+ '<i class="gbtn-icon"></i>'
									+ '<p class="after">'
									+ json[i].totalMoney
									+ '元&nbsp;&nbsp;'
									+ json[i].businessDesc
									+ '</p>'
									+ '<p class="before">原价：<del class="originPrice"><span>'
									+ json[i].oldMoney
									+ '元</span></del></p>'
									+ '</div>';
							str += str1;

						}

						for ( var i = tj_len; i < (json.length); i++) {
							var str1 = '<div class="list-busi-wrap info_hid" data-marketProgId="'
									+ json[i].marketProgId
									+ '" data-marketKindId="'
									+ json[i].marketKindId
									+ '" data-months="'
									+ json[i].months
									+ '" data-productid="'
									+ json[i].productId
									+ '" data-privilege="'
									+ json[i]['promotionDesc']
									+ '" data-money="'
									+ json[i].totalMoney
									+ '" data-start="'
									+ json[i].showEffTime
									+ '" data-end="'
									+ json[i].showExpTime
									+ '" data-busi="'
									+ json[i].businessDesc
									+ '" data-detail="'
									+ json[i].totalMoney
									+ '元/'
									+ json[i].businessDesc
									+ '" data-pid="'
									+ json[i].offerVasId
									+ '">'
									+ '<i class="gbtn-icon"></i>'
									+ '<p class="after">'
									+ json[i].totalMoney
									+ '元&nbsp;&nbsp;'
									+ json[i].businessDesc
									+ '</p>'
									+ '<p class="before">原价：<del class="originPrice"><span>'
									+ json[i].oldMoney
									+ '元</span></del></p>'
									+ '</div>';
							str += str1;
						}

						len2 = '<div class="my-list-busi  my-list-busi-kdxb-main">'
								+ '<div class="list-busi" id="J_list">'
								+ str
								+ '</div>' + '</div>';

						buffer = len1 + len2;

					}
					return new Handlebars.SafeString(buffer);
				});

Handlebars
		.registerHelper(
				"isAd1",
				function(json, fn) {
					var buffer = '';
					var len1 = '';
					var len2 = '';

					if (json.length == 0) {
						buffer = '';
					} else {
						len1 = '<div class="title-wrap" style="margin:0;color:#333333"><span style="padding: 20px 15px 0 20px;line-height: 13px;font-size:13px;">融合套餐</span></div><div id="rh_content_con">';

						for ( var i = 0; i < (json.length); i++) {
							len2 += '<div class="list-busi-wrap info_hid"  data-mk_plan_desc="'
									+ json[i]['MK_PLAN_DESC']
									+ '" data-mk_plan_domain="'
									+ json[i]['MK_PLAN_DOMAIN']
									+ '"   data-mk_plan_name="'
									+ json[i]['MK_PLAN_NAME']
									+ '"    data-mk_plan_rea_price="'
									+ json[i]['MK_PLAN_REA_PRICE']
									+ '"  data-mk_plan_rec_price="'
									+ json[i]['MK_PLAN_REC_PRICE']
									+ '"  data-mk_plan_rel_plan="'
									+ json[i]['MK_PLAN_REL_PLAN']
									+ '"  data-mk_plan_rel_vas_plan="'
									+ json[i]['MK_PLAN_REL_VAS_PLAN']
									+ '"  data-mk_plan_tag="'
									+ json[i]['MK_PLAN_TAG']
									+ '"  data-mk_plan_type="'
									+ json[i]['MK_PLAN_TYPE']
									+ '"  data-mk_program_id="'
									+ json[i]['MK_PROGRAM_ID']
									+ '"  data-mk_program_kind_id="'
									+ json[i]['MK_PROGRAM_KIND_ID']
									+ '"  data-plan_name="'
									+ json[i]['PLAN_NAME']
									+ '"  data-region_id="'
									+ json[i]['REGION_ID']
									+ '" data-ext1="'
									+ json[i]['EXT1']
									+ '"  data-vas_plan_name="'
									+ json[i]['VAS_PLAN_NAME']
									+ '"  >'
									+ '<i class="gbtn-icon"></i>'
									+ '<p class="after">'
									+ json[i].EXT1
									+ '</p>' + '</div>';

						}
						len2 += '<div/>';

					}

					buffer = len1 + len2;

					return new Handlebars.SafeString(buffer);
				});

// 加载裸宽信息
Handlebars
		.registerHelper(
				"isLk",
				function(json, fn) {
					var buffer = '';
					var tj_len = 5;
					var len1 = '';
					var len2 = '';
					if (json.length == 0) {
						buffer = '';
					} else if (json.length == 1) {
						len1 = '<div class="lk-busi-warp"  data-privilege="'
								+ json[0]['promotionDesc']
								+ '" data-isfusion = "0" data-totalmoney="'
								+ json[0]['totalMoney']
								+ '"  data-offervasid="'
								+ json[0]['offerVasId']
								+ '"   data-offerproid="'
								+ json[0]['offerProId']
								+ '"  data-offerbaseid="'
								+ json[0]['offerBaseId']
								+ '"    data-offercostexplain="'
								+ json[0]['promotionDesc']
								+ '"    data-marketkinddesc="'
								+ json[0]['businessDesc']
								+ '" data-marketkindid="'
								+ json[0]['marketKindId']
								+ '"  data-marketprogid="'
								+ json[0]['marketProgId']
								+ '" > '
								+ '	<i class="gbtn-icon gbtn-icon-hid"></i> '
								+ '	<p class="after">'
								+ json[0].totalMoney
								+ '元 '
								+ json[0].businessDesc
								+ ' </p>  '
								+ '	<p class="before">原价：<del class="originPrice">'
								+ '<span style="color:#333333;">'
								+ json[0].oldMoney + '元</del></p> '
								+ '	</div> ';
						buffer = len1;
					} else if (json.length > 1 && json.length < tj_len) {
						for ( var i = 0; i < json.length; i++) {
							var str1 = '<div class="lk-busi-warp " data-privilege="'
									+ json[i]['promotionDesc']
									+ '"  data-isfusion = "0" data-totalmoney="'
									+ json[i]['totalMoney']
									+ '" data-offervasid="'
									+ json[i]['offerVasId']
									+ '" data-offerproid="'
									+ json[i]['offerProId']
									+ '" data-offerbaseid="'
									+ json[i]['offerBaseId']
									+ '" data-offercostexplain="'
									+ json[i]['promotionDesc']
									+ '" data-marketkinddesc="'
									+ json[i]['businessDesc']
									+ '" data-marketkindid="'
									+ json[i]['marketKindId']
									+ '" data-marketprogid="'
									+ json[i]['marketProgId']
									+ '"> '
									+ '	<i class="gbtn-icon gbtn-icon-hid"></i> '
									+ '	<p class="after">'
									+ json[i].totalMoney
									+ '元 '
									+ json[i].businessDesc
									+ ' </p>  '
									+ '	<p class="before">原价：<del class="originPrice">'
									+ '<span style="color:#333333;">'
									+ json[i].oldMoney
									+ '元</del></p> '
									+ '	</div> ';
							len1 += str1;
						}
						buffer = len1;

					} else if (json.length > tj_len || json.length == tj_len) {
						for ( var i = 0; i < tj_len - 1; i++) {
							var str1 = '<div class="lk-busi-warp " data-privilege="'
									+ json[i]['promotionDesc']
									+ '"  data-isfusion = "0" data-totalmoney="'
									+ json[i]['totalMoney']
									+ '" data-offervasid="'
									+ json[i]['offerVasId']
									+ '" data-offerproid="'
									+ json[i]['offerProId']
									+ '" data-offerbaseid="'
									+ json[i]['offerBaseId']
									+ '" data-offercostexplain="'
									+ json[i]['promotionDesc']
									+ '" data-marketkinddesc="'
									+ json[i]['businessDesc']
									+ '" data-marketkindid="'
									+ json[i]['marketKindId']
									+ '" data-marketprogid="'
									+ json[i]['marketProgId']
									+ '"> '
									+ '	<i class="gbtn-icon gbtn-icon-hid"></i> '
									+ '	<p class="after">'
									+ json[i].totalMoney
									+ '元 '
									+ json[i].businessDesc
									+ ' </p>  '
									+ '	<p class="before">原价：<del class="originPrice">'
									+ '<span style="color:#333333;">'
									+ json[i].oldMoney
									+ '元</del></p> '
									+ '	</div> ';

							len1 += str1;
						}

						for ( var i = tj_len - 1; i < (json.length); i++) {
							var str2 = '<div class="lk-busi-warp lk-busi-warp-hid" data-privilege="'
									+ json[i]['promotionDesc']
									+ '"  data-isfusion = "0" data-totalmoney="'
									+ json[i]['totalMoney']
									+ '" data-offervasid="'
									+ json[i]['offerVasId']
									+ '" data-offerproid="'
									+ json[i]['offerProId']
									+ '" data-offerbaseid="'
									+ json[i]['offerBaseId']
									+ '" data-offercostexplain="'
									+ json[i]['promotionDesc']
									+ '" data-marketkinddesc="'
									+ json[i]['businessDesc']
									+ '" data-marketkindid="'
									+ json[i]['marketKindId']
									+ '" data-marketprogid="'
									+ json[i]['marketProgId']
									+ '"> '
									+ '	<i class="gbtn-icon gbtn-icon-hid"></i> '
									+ '	<p class="after">'
									+ json[i].totalMoney
									+ '元 '
									+ json[i].businessDesc
									+ ' </p>  '
									+ '	<p class="before">原价：<del class="originPrice">'
									+ '<span style="color:#333333;">'
									+ json[i].oldMoney
									+ '元</del></p> '
									+ '	</div> ';
							len1 += str2;
						}
						buffer = len1;

					}

					return new Handlebars.SafeString(buffer);

				});

// 宽带开户--关联档次及其产品展示
Handlebars.registerHelper('showDomType',function(productInfos, offerId) {
	var str = "";
	var productInfosLen = productInfos.length;
	for ( var i = 0; i < productInfosLen; i++) {
		var attrs = productInfos[i].attrs;
		var attrsLen = attrs.length;
		for ( var j = 0; j < attrsLen; j++) { 
			var selectList = attrs[j].selectList;
			var selectListLen = selectList.length;
			if (attrs[j].editType == "1") {
				str += "<div class='busi-info-cells clearfix'><span class='ui-label'>"
						+ attrs[j].attrName
						+ ":</span><input class='ui-input ui-input-medium'" + "edit-type=" + attrs[j].editType +" disabled='true' offer-id=" + offerId + ">"
						+ attrs[j].selectList
						+ "</input></div>";
			} else if (attrs[j].editType == "2") {
				if( !$.isEmptyObject(attrs[j].selectList) ){
					str += "<div class='busi-info-cells clearfix'><span class='ui-label'>"
							+ attrs[j].attrName
							+ ":</span><select attrId='" + attrs[j].attrId + "' class=' edit-type-two'>";
					for ( var k = 0; k < selectListLen; k++) {
						str += "<option value='"
								+ selectList[k].value + "'>"
								+ selectList[k].text
								+ "</option>";
					}
					str += "</select></div>";
				}
			} else if (attrs[j].editType == "3") {
				str += "<div class='busi-info-cells clearfix'><span class='ui-label'>"
						+ attrs[j].attrName
						+ ":</span><input type='password'>"
						+ attrs[j].selectList
						+ "</input></div>";
			} else if (attrs[j].editType == "4") {
				str += "<div class='busi-info-cells clearfix none'><span class='ui-label'>"
						+ attrs[j].attrName
						+ ":</span><input type='hidden'>"
						+ attrs[j].selectList
						+ "</input></div>";
			} else if (attrs[j].editType == "5") {
				str += "<div class='busi-info-cells clearfix'><span class='ui-label'>"
						+ attrs[j].attrName
						+ ":</span><input class='ui-input ui-input-medium'" + "edit-type=" + attrs[j].editType +" disabled='true' offer-id=" + offerId + ">"
						+ attrs[j].selectList
						+ "</input></div>";
			} else if (attrs[j].editType == "6") {
				// 弹出框?
			} else if (attrs[j].editType == "7") {
				str += "<div class='busi-info-cells clearfix'><span class='ui-label'>"
						+ attrs[j].attrName
						+ ":</span><input type='radio'"
						+ attrs[j].selectList
						+ "</input></div>";
			} else {
				// 出错？
			}
		}
	}
	return new Handlebars.SafeString(str);
});

// isRh

Handlebars.registerHelper("isRh", function(json, fn) {
	var buffer = '';
	var len1 = '';

	if (json.length == 0) {
		buffer = '';
	} else {
		for ( var i = 0; i < json.length; i++) {
			var str1 = '<div class="lk-busi-warp " data-privilege="'
					+ json[i]['promotionDesc'] + '" data-ext1="'
					+ json[i]['EXT1']
					+ '" data-isfusion = "1" data-totalmoney="'
					+ json[i]['totalMoney'] + '" data-offervasid="'
					+ json[i]['offerVasId'] + '" data-offerproid="'
					+ json[i]['offerProId'] + '" data-offerbaseid="'
					+ json[i]['offerBaseId'] + '" data-offercostexplain="'
					+ json[i]['promotionDesc'] + '" data-marketkinddesc="'
					+ json[i]['businessDesc'] + '" data-marketkindid="'
					+ json[i]['marketKindId'] + '" data-marketprogid="'
					+ json[i]['marketProgId'] + '"> '
					+ '	<i class="gbtn-icon gbtn-icon-hid" ></i> '
					+ '	<p class="after">' + json[i].EXT1 + ' </p>  '
					+ '	</div> ';
			len1 += str1;
		}
		buffer = len1;
	}
	return new Handlebars.SafeString(buffer);
});

// 宽带首页 裸宽信息展示
Handlebars
		.registerHelper(
				"isFrontLk",
				function(json, fn) {
					var buffer = '';
					var len1 = '';

					if (json.length == 0) {
						buffer = '';
					} else {
						for ( var i = 0; i < json.length; i++) {
							var str1 = '<div class="content " style="background:#FABDD1;"  > '
									+ '<div class="item-left">'
									+ '	<p class="item-content-title">'
									+ json[i].totalMoney
									+ '元 '
									+ json[i].businessDesc
									+ ' </p>  '
									+ '	<i class="item-content-desc">'
									+ json[i].promotionDesc
									+ '</i> '
									+ '</div>'
									+ '<div class="item-right" data-privilege="'
									+ json[i]['promotionDesc']
									+ '"  data-isfusion = "0" data-totalmoney="'
									+ json[i]['totalMoney']
									+ '" data-offervasid="'
									+ json[i]['offerVasId']
									+ '" data-offerproid="'
									+ json[i]['offerProId']
									+ '" data-offerbaseid="'
									+ json[i]['offerBaseId']
									+ '" data-offercostexplain="'
									+ json[i]['promotionDesc']
									+ '" data-marketkinddesc="'
									+ json[i]['businessDesc']
									+ '" data-marketkindid="'
									+ json[i]['marketKindId']
									+ '" data-marketprogid="'
									+ json[i]['marketProgId']
									+ '"><img src="res/css/img/clickFinger.png"><i class="item-right-cont">点击办理</i></div>'
									+ '</div> ';
							len1 += str1;
						}
						buffer = len1;
					}
					return new Handlebars.SafeString(buffer);
				});

// 宽带首页 融合信息展示
Handlebars
		.registerHelper(
				"isFrontRh",
				function(json, fn) {
					var buffer = '';
					var len1 = '';

					if (json.length == 0) {
						buffer = '';
					} else {
						for ( var i = 0; i < json.length; i++) {
							var str1 = '<div class="content " style="background:#F4C649;" > '
									+ '<div class="item-left">'
									+ '	<p class="item-content-title">'
									+ json[i].totalMoney
									+ '元 '
									+ json[i].businessDesc
									+ ' </p>  '
									+ '	<i class="item-content-desc">'
									+ json[i].promotionDesc
									+ '</i> '
									+ '</div>'
									+ '<div class="item-right" data-privilege="'
									+ json[i]['promotionDesc']
									+ '"  data-isfusion = "1" data-totalmoney="'
									+ json[i]['totalMoney']
									+ '" data-offervasid="'
									+ json[i]['offerVasId']
									+ '" data-offerproid="'
									+ json[i]['offerProId']
									+ '" data-offerbaseid="'
									+ json[i]['offerBaseId']
									+ '" data-offercostexplain="'
									+ json[i]['promotionDesc']
									+ '" data-marketkinddesc="'
									+ json[i]['businessDesc']
									+ '" data-marketkindid="'
									+ json[i]['marketKindId']
									+ '" data-marketprogid="'
									+ json[i]['marketProgId']
									+ '"><img src="res/css/img/clickFinger.png"><i class="item-right-cont">点击办理</i></div>'
									+ '</div> ';
							len1 += str1;
						}
						buffer = len1;
					}
					return new Handlebars.SafeString(buffer);
				});

// 判断时候已读返回class
Handlebars.registerHelper('orderHight', function(value, fn) {
	if (value == "1") {
		return "doing";
	}
	if (value == "2") {
		return "success";
	} else if (value == "3") {
		return "error";
	}
});
Handlebars.registerHelper('orderString', function(value, fn) {

	if (value == "1") {
		return "正在处理";

	}
	if (value == "2") {
		return "交易成功";

	} else if (value == "3") {
		return "交易失败";
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
	array = array ? array : [ '' ];
	for ( var i = 0, j = array.length; i < j; i++) {
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
		content += '..';
	}
	return new Handlebars.SafeString(content);
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