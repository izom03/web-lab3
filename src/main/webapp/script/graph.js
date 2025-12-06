function handleSvgClick(e) {
    const svg = e.currentTarget;
    const pt = svg.createSVGPoint();
    pt.x = e.clientX;
    pt.y = e.clientY;
    const svgP = pt.matrixTransform(svg.getScreenCTM().inverse());

    const normX = svgP.x / svg.width.baseVal.value * 2 * 1.25;
    const normY = -svgP.y / svg.height.baseVal.value * 2 * 1.25;

    document.getElementById('graphForm:cx').value = normX;
    document.getElementById('graphForm:cy').value = normY;

    mojarra.ab(
        document.getElementById('graphForm:clickBtn'),
        e,
        'action',
        '@form',
        'main-panel'
    );
}
