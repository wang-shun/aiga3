<div class="row">
    {{#each this}}
    <div class="col-md-3">
        <input class="minimal" type="checkbox" {{isChecked isShow}} id="kpiList-{{@index}}" value="{{kpiId}}"/>
        <label for="kpiList-{{@index}}">{{shrinkHtmlFilter ext1 10}}</label>
    </div>
    {{/each}}
</div>