 <div class="mxgif-wrapper">
    {{#each content}}
    <div class="mxgif-container fn-clear {{mxgifWidth item}}">
        {{#if isNodeName}}
        <div class="mxgif-sidebar"><p>{{name}}</p></div>
        {{/if}}
        <div class="mxgif-lists {{mxgifHeight1 item}}">
            <div class="mxgif-lists-content {{mxgifLength item}}">
                {{#each item}}
                    {{#if isNodeName}}
                        {{#isNodeNameCompare isNodeName 1}}
                        <div class="mxgif-container fn-clear">
                            <div class="mxgif-sidebar {{mxgifWidth item}}"><p>{{name}}</p></div>
                            <div class="mxgif-lists {{mxgifHeight2 item}}">
                                <div class="mxgif-lists-content {{mxgifLength item}}">
                                    {{#each item}}
                                    <div class="mxgif-lists-item">{{name}}</div>
                                    {{/each}}
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
    {{/each}}
</div>