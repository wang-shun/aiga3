<table class="table table-bordered table-hover" style="width: 2400px;">
    <tbody>
        {{#each this}}	        
        
	        <li name="initImage"><input type="radio" class="minimal" value="{{Id}}" name="Id" style="display:none">
	        	<div class="img_block">
	            	<img src="{{imgSrc}}" />
	                <a href="#" rel="lightbox[plants]" title="测试标题" class="zoom">放大</a>
	                <a href="#" class="ilike" name="ilike">共享</a>
	      		</div>
	            <h3>{{title}}</h3>
	            <div class="iNum"><span>{{likeCount}}</span><a href="#">{{commentCount}}</a></div>
	          	<p>{{description}}</p>
			</li>
    	{{/each}}
    </tbody>
</table>