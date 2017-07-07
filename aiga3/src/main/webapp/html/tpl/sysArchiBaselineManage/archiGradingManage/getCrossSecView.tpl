 <div class="mxgif-wrapper">
    {{#each this}}
    <div class="mxgif-container fn-clear {{mxgifWidth item}}" id="cross_{{id}}">
        {{#if isNodeName}}
        <div class="mxgif-sidebar"><p>{{name}}</p></div>
        {{/if}}
        <div class="mxgif-lists {{mxgifHeight1 item}}">
            <div class="mxgif-lists-content {{mxgifLength item}} {{setMxgifMinHeight isNodeName isCross}}">
                <div name="crossContent" class="mxgif-position-cross-content {{isShowPosition item}}"></div>
                <div class="mxgif-cross-content {{mxgifFloatWidth item}}" name="noCrossContent">
                {{#each item}}
                    {{#if isNodeName}}
                        {{#isNodeNameCompare isNodeName 1}}
                        <div class="mxgif-container fn-clear" id="cross_{{id}}">
                            <div class="mxgif-sidebar {{mxgifWidth item}}"><p>{{name}}</p></div>
                            <div class="mxgif-lists {{mxgifHeight1 item}} {{setMxgifMinHeight isNodeName isCross}}">
                                <div class="mxgif-lists-content {{mxgifLength item}}">
                                    <div class="mxgif-cross-content width-auto" name="noCrossContent">
                                    {{#each item}}
                                    <div class="mxgif-lists-item">{{name}}</div>
                                    {{/each}}
                                    </div>
                                </div>
                            </div>
                        </div>
                        {{else}}
                            <div class="mxgif-lists-item">{{name}}</div>
                        {{/isNodeNameCompare}}
                    {{/if}}
                {{/each}}
                </div>
            </div>
        </div>
    </div>
    {{/each}}
</div>