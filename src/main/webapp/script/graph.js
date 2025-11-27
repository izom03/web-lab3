document.getElementById('click_catcher').onclick = function(e){
    let svgRect = this.getBoundingClientRect();
    let clickX = e.clientX - svgRect.left;
    let clickY = e.clientY - svgRect.top;

    let svgX = clickX - svgRect.width/2;
    let svgY = svgRect.height/2 - clickY;

    let normX = svgX / svgRect.width * 2 * 1.25;
    let normY = svgY / svgRect.height * 2 * 1.25;
    
    document.getElementById('graphForm:cx').value = normX;
    document.getElementById('graphForm:cy').value = normY;

    mojarra.ab(
        document.getElementById('graphForm:clickBtn'),
        e,
        'action',
        '@form',
        'graph-panel result-table'
    );
};
