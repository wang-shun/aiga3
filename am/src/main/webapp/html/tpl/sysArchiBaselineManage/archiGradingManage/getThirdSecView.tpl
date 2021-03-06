 <div class="mxgif-wrapper mxgif-third">
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
                                        {{#if item}}
                                            <div class="mxgif-lists-item" >
                                                <h3 class="mxgif-item-title">{{name}}</h3>
                                                {{#each item}}
                                                    <div class="mxgif-third-item" style="background: {{bgColor}}">{{name}}</div>
                                                {{/each}}
                                            </div>
                                        {{else}}
                                            <div class="mxgif-lists-item">{{name}}</div>
                                        {{/if}}
                                    {{/each}}
                                </div>
                            </div>
                        </div>
                        {{else}}
                            {{#if item}}
                                <div class="mxgif-lists-item" >
                                    <h3 class="mxgif-item-title">{{name}}</h3>
                                    {{#each item}}
                                        <div class="mxgif-third-item" style="background: {{bgColor}}">{{name}}</div>
                                    {{/each}}
                                </div>
                            {{else}}
                                <div class="mxgif-lists-item">{{name}}</div>
                            {{/if}}
                        {{/isNodeNameCompare}}
                    {{/if}}
                {{/each}}
            </div>
        </div>
    </div>
    {{/each}}
    <div class="mxgif-state-lists">
        {{#each stateItems}}
        <span class="mxgif-state-item" style="background: {{ext1}}">{{codeName}}</span>
        {{/each}}
    </div>
</div>