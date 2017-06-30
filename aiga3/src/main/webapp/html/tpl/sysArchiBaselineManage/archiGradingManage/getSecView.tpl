 <div class="mxgif-wrapper" >
    {{#each this}}
    <div class="mxgif-container {{mxgifWidth item}}">
        {{#if isNodeName}}
        <div class="mxgif-sidebar"><p>{{name}}</p></div>
        {{/if}}
        <div class="mxgif-lists {{mxgifHeight1 item}}">
            <div class="mxgif-lists-content {{mxgifLength item}}">
                {{#each item}}
                    {{#if isNodeName}}
                    <div class="mxgif-container">
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
                     <div class="mxgif-lists-item ">{{name}}</div>
                    {{/if}}
                {{/each}}
            </div>
        </div>
    </div>
    {{/each}}
</div>