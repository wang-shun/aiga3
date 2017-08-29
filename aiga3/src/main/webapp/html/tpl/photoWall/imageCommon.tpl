<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}
	        <li name="initImage">
	        	<div class="img_block">
	            	<img src="{{imgSrc}}" />
	                <a href="#" rel="lightbox[plants]" title="测试标题" class="zoom">放大</a>
	                <a href="#" class="ilike" name="ilike" imgid="{{id}}">取消</a>
	      		</div>
	            <h3>{{title}}</h3>
	            <div class="iNum"><span>{{likeCount}}</span><a href="#">{{commentCount}}</a></div>
	          	<p>{{description}}</p>
			</li>
    	{{/each}}
    </tbody>
</table>
