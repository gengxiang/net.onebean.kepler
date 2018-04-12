function initModalRU() {
    /*猎犬 异步数据*/
    var rolenames = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('ch_name'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {//在文本框输入字符时才发起请求
            url:'/sysrole/findbyname?name=%QUERY',
            wildcard: '%QUERY'
            ,filter: function(result) {
                return $.map(result.data, function(item) {
                    return {
                        ch_name:item.ch_name,
                        name:item.name,
                        id:item.id
                    };
                });
            }
        }
    });

    rolenames.initialize();
    /*tags input初始化*/
    $('#input-sg').tagsinput(
        {
            typeaheadjs: {
                displayKey: 'ch_name',
                source: rolenames.ttAdapter()
            },
            freeInput:false,
            trimValue: 'true',
            itemValue: 'id',
            itemText: 'ch_name'}
    );
}



/*初始化RU列表*/
function initRoleUserList() {
    var userId = $('#rolesList').data("uid")
    doGet("/sysrole/findbyuid",{userId:userId},function(res){
        $('#rolesList').html(template('tpl-sysRoleList',res.data));
    })
}

/*初始化RU列表*/
function modalRU(uid,name){
    $('#rolesList').data("uid",uid);
    $(".data-bind-title").html("为用户: "+name+" 绑定角色");
    initRoleUserList();
    $('#data-bind-modal').modal('open');
}

/*添加RU*/
function addRU() {
    var userId = $('#rolesList').data("uid")
    var $val = $('#input-sg').val();
    if($val.length>0){
        doPost("/sysrole/addroleuser",{userId:userId,roleIds:$val},function(res){
            if(res.flag){
                initRoleUserList(userId);
                $('#input-sg').tagsinput('removeAll');
            }
        })
    }
}
/*删除RU*/
function removeRU() {
    var userId = $('#rolesList').data("uid")
    var $rlist = $('input[name="roleList"]')
    var ids = "";
    $.each($rlist, function(key, val) {
        if($(val).is(':checked')){
            ids += $(val).val()+",";
        }
    });
    ids = ids.substr(0,ids.length-1)
    doPost("/sysrole/removeroleuser",{urIds:ids},function(res){
        if(res.flag){
            initRoleUserList(userId);
        }
    })
}


/*模态机构树*/
function modalOrgTree() {
    var $treeTips = $('#treeTips');
    $treeTips.html(template('treeTipsTempl',null));
    initTreeAsyncSingleSelect("请选择机构",null,"/sysorg/orgtree");
    treeTipsModal($treeTips);
}