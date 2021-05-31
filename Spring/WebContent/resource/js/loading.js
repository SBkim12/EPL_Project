function loading_st() {
	layer_str = "<div id='loading_layer' style='display:block; position:absolute; text-align:center; width:100%;'><img src='../resource/images/loading-img.gif'/></div>"
	document.write(layer_str);
}
function loading_ed() {
	var ta = document.getElementById('loading_layer');
	ta.style.display = 'none';
}
loading_st();
window.onload = loading_ed;
