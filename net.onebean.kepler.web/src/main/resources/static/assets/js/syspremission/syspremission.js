/*模态菜单树*/
function modalMenuTree(pid) {
    var $treeTips = $('#treeTips');
    $treeTips.html(template('treeTipsTempl',null));
    initTreeAsyncSingleSelect("请选择菜单",pid,"/syspremission/menutree");
    treeTipsModal($treeTips);
}