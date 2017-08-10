前端遗留问题修复
---------------------

1. 页面唯一标示的问题（Page），注意各种方式的命名问题（弹框，模板）
2. 服务器端真分页的使用事项，到底哪些需要真分页，具体可直接问应老板
	2.1 真分页无数据时的提示，假分页公用方法的统一
3. 页面交互和视觉的统一处理（按钮和表单排列）
	3,1 表单的大小使用`input-sm`,栅格布局使用`col-md`
4. Tpl从单独文件放置到当前页面注意事项（解决遗留问题）
5. 页面整体布局的使用，和细节的处理。row包裹，按钮的间距，不在使用空格等。
6. 公用方法的熟悉，使用，以及后续的维护，Utils.js
7. 横向滚动条的使用问题。


### 常见问题汇总
1. 接口调用出现异常（有时候会调用成功，有时候会失败）

> 这个情况是接口名称冲突导致，定义了两个相同的接口名，但是调用的后台方法却不是同一个，所以会导致接口的稳定性。

解决办法：保持所有接口名称的唯一性，不能冲突。

