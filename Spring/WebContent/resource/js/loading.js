function loading_st() {
	layer_str = "<div id='loading_layer' style='position: fixed;z-index: 3000;top: 0;left: 0;width: 100%;height: 100%;display: flex;justify-content: center;align-items: center;background-color: rgb(123 123 123 / 40%);writing-mode: vertical-lr;'><img style='width:30%' src='../resource/images/loading_to.gif'/><img style='width:30%' src='../resource/images/loading_text.gif'/></div>"
	document.write(layer_str);
}
function loading_ed() {
	var ta = document.getElementById('loading_layer');
	ta.style.display = 'none';
}
function reloading_st() {
	var re = document.getElementById('loading_layer');
	re.style.display = 'flex';
}
loading_st();
window.onload = loading_ed;
