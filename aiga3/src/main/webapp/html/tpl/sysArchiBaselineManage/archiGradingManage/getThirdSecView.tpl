 <div class="mxgif-wrapper mxgif-third">
    {{#each content}}
    <div class="mxgif-container fn-clear {{mxgifWidth item}}">
        {{#if isNodeName}}
        <div class="mxgif-sidebar"><p>{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</p></div>
        {{/if}}
        <div class="mxgif-lists {{mxgifHeight1 item}}">
            <div class="mxgif-lists-content {{mxgifLength item}}">
                {{#each item}}
                    {{#if isNodeName}}
                        {{#isNodeNameCompare isNodeName 1}}
                        <div class="mxgif-container fn-clear">
                            <div class="mxgif-sidebar {{mxgifWidth item}}"><p>{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</p></div>
                            <div class="mxgif-lists {{mxgifHeight2 item}}">
                                <div class="mxgif-lists-content {{mxgifLength item}}">
                                    {{#each item}}
                                       {{#if isNodeName}}
                                            {{#isNodeNameCompare isNodeName 1}}
                                            <div class="mxgif-container fn-clear">
                                                <div class="mxgif-sidebar {{mxgifWidth item}}"><p>{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</p></div>
                                                <div class="mxgif-lists {{mxgifHeight2 item}}">
                                                    <div class="mxgif-lists-content {{mxgifLength item}}">
                                                        {{#each item}}
                                                            {{#if item}}
                                                                <div class="mxgif-lists-item" >
                                                                    <h3 class="mxgif-item-title">{{name}}</h3>
                                                                    {{#each item}}
                                                                        <div class="mxgif-third-item" style="background: {{bgColor}}">{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</div>
                                                                    {{/each}}
                                                                </div>
                                                            {{else}}
                                                                <div class="mxgif-lists-item">{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</div>
                                                            {{/if}}
                                                        {{/each}}
                                                    </div>
                                                </div>
                                            </div>
                                            {{else}}
                                                {{#if item}}
                                                    <div class="mxgif-lists-item"  style="float: none;display: inline-block;">
                                                        <h3 class="mxgif-item-title">{{name}}</h3>
                                                        {{#each item}}
                                                            <div class="mxgif-third-item" style="background: {{bgColor}}">{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</div>
                                                        {{/each}}
                                                    </div>
                                                {{else}}
                                                    <div class="mxgif-lists-item">{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</div>
                                                {{/if}}
                                            {{/isNodeNameCompare}}
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
                                        <div class="mxgif-third-item" style="background: {{bgColor}}">{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</div>
                                    {{/each}}
                                </div>
                            {{else}}
                                <div class="mxgif-lists-item">{{#if isNodeName}}{{#isAppCompare mediaType}}<i class="fa fa-fw fa-mobile text-yellow"></i>{{/isAppCompare}}{{/if}}{{name}}</div>
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