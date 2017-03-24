                                <div class="box-header with-border">
                                    <h3 class="box-title">测试报告</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>报告名称<i class="text-red">*</i></label>
                                                <input type="email" class="form-control input-sm" name="reportName" placeholder="请输入报告名称">
                                                <input type="hidden" value="{{creatorId}}" name="creatorId">
                                                <input type="hidden" value="{{taskId}}" name="taskId">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>总用例数</label>
                                                <input type="email" class="form-control input-sm" name="totalCase" value="{{totalCase}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>已执行用例数</label>
                                                <input type="email" class="form-control input-sm" name="noneRunCase" value="{{noneRunCase}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>未执行用例数</label>
                                                <input type="email" class="form-control input-sm" name="hasRunCase" value="{{hasRunCase}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>正确完成</label>
                                                <input type="email" class="form-control input-sm" name="successCase" value="{{successCase}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-1">
                                            <div class="form-group">
                                                <label></label>
                                                <button type="button" class="btn btn-primary" data-toggle="modal" name="save">保存报告</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>失败完成</label>
                                                <input type="email" class="form-control input-sm" name="failCase" value="{{failCase}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>成功率</label>
                                                <input type="email" class="form-control input-sm" name="successRate" value="{{getSuccessRate successCase totalCase}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>开始时间</label>
                                                <input type="email" class="form-control input-sm" name="beginTime" value="{{beginTime}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>结束时间</label>
                                                <input type="email" class="form-control input-sm" name="endTime" value="{{endTime}}" readonly="readonly">
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <label>执行用时</label>
                                                <input type="email" class="form-control input-sm" name="spendTime" value="{{spendTime}}" readonly="readonly">
                                            </div>
                                        </div>
                                    </div>
                                </div>