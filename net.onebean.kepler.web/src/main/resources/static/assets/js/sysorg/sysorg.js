/*模态机构树*/
function modalOrgTree(selfId) {
    var $treeTips = $('#treeTips');
    $treeTips.html(template('treeTipsTempl',null));
    initTreeAsyncSingleSelect("请选择机构",selfId,"/sysorg/orgtree");
    treeTipsModal($treeTips);
}