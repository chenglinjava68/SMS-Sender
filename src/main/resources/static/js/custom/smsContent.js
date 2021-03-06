var url = {
    commonPre: "/smsContent",//通用前缀
    list: '/list/',//分页查询
    add: '/add',//新增
    delete: '/delete',//文件删除
    likeSearchByName: '/search/name/',//根据名字模糊查询
    query: '/query',//查询单个
    updateInfo: '/update',//修改信息
};
var smsContentList = {
    //分页查询
    page: function (pageNo) {
        if(!pageNo)
            pageNo = 1;
        $.get(url.commonPre + url.list + pageNo, function (result) {
            if (common.isJson(result))
                common.showMessage(result.message)
            else
                $('#tableContent').html(result);
        });
    },

    //弹出新增模态框
    openAddModal : function () {
        common.closeModal($('#addForm')[0], null);
        $('#addModal').modal();
    },

    //新增
    add : function () {
        $.post(url.commonPre + url.add,$('#addForm').serialize(),function (result) {
            if(!common.errorHandle(result)) {
                smsContentList.page();
                common.successHandle();
                common.closeModal($('#addForm')[0], $('#addModal'));
            }
        } );
    },

    //批量删除
    batchDelete: function () {
        //获取被选中的复选框value数组
        var array = common.getCheckedArray();
        if(array == false){
            common.showMessage("请选择要删除的记录");
            return;
        }
        //确认框
        $('#confirmModal').modal({
            relatedTarget: this,
            onConfirm: function(options) {
                $.post(url.commonPre + url.delete,{ids:array},function (result) {
                    //执行处理方法,如果未执行,表示成功
                    if(!common.errorHandle(result)){
                        smsContentList.page();
                        common.successHandle();
                        //取消全选
                        common.checkboxAll(false);
                    }
                });
            },
            // closeOnConfirm: false,
            onCancel: function() {
            }
        });
    },

    //根据名字模糊查询
    likeSearchByName : function() {
        $('#searchByUsernameInput').change(function () {
            var name = $(this).val();
            if(!name)
                return;
            $.get(url.commonPre  + url.likeSearchByName + name, function (result) {
                $('#tableContent').html(result);
            });
        });
    },
    //点击搜索框，清空内容
    searchUserInputClean: function (input) {
        //当前内容
        var tempStr = input.val();
        //清空内容
        input.val("");
        //如果之前是有内容的，就重新加载第一页
        if (tempStr)
            smsContentList.page(1);
    },

    //弹出修改模态框
    openUpdateModal: function () {
        var ids = common.getCheckedArray();
        if(ids == false){
            common.showMessage("请选择要修改的记录");
            return;
        }else if(ids.length > 1){
            common.showMessage("请选择要单条记录");
            //取消全选
            common.checkboxAll(false);
            return;
        }
        common.checkboxAll(false);
        $.post(url.commonPre + url.query,{id:ids[0]},function (result) {
            if(!common.errorHandle(result)){
                $('#updateForm :input[name="id"]').val(result.data.id);
                $('#updateForm :input[name="name"]').val(result.data.name);
                $('#updateForm :input[name="content"]').val(result.data.content);
                $('#updateForm :input[name="remark"]').val(result.data.remark);
                $('#updateModal').modal();
            }

        });
    },
    //修改信息
    updateInfo :function() {
        $.post(url.commonPre + url.updateInfo,$('#updateForm').serialize(),function (result) {
            if(!common.errorHandle(result)){
                smsContentList.page();
                common.successHandle();
                common.closeModal($('#updateForm')[0], $('#updateModal'));
            }
        } );
    },

};
$(function () {
    smsContentList.page();
    smsContentList.likeSearchByName();
});