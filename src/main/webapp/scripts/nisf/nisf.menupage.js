/**
 * @fileoverview 메뉴 (url, topmenu, leftmenu) 이동
 * @version 0.1
 * @since 2011.10.01
 */

/**
 * url을 이용한 페이지 이동
 *
 * @param url
 */
function goMenuPage(url) {
	if(url==null || url=='')return;

	var f = makeform(url);

	f.submit();
}

/**
 * top메뉴 클릭 이동
 *
 * @param url 이동 url
 * @param menuNo 메뉴번호
 * @param selectMenuNo 선택메뉴번호
 * @param menuClass 메뉴이미지번호
 */
function goTopMenuPage(url, menuNo, selectMenuNo,menuClass) {

	var f = makeform(url);

	if(menuClass!=null){
		f.appendChild(AddData('menuClass', menuClass));
	}
	if(selectMenuNo!=null){
		f.appendChild(AddData('selectMenuNo', selectMenuNo));
	}
	f.appendChild(AddData('paramMenuNo', menuNo));
	f.submit();
}

/**
 * left 메뉴 클릭 이동
 *
 * @param url 이동 url
 * @param menuNo 메뉴번호
 * @param selectMenuNo 선택메뉴번호
 * @param menuClass 메뉴이미지번호
 */
function goLeftMenuPage(url, menuNo, selectMenuNo, menuClass) {
	if(url==null || url=='')return;

	if(url.indexOf('popup:')>-1){
		url=url.substring('popup:'.length,url.length);
		openFullWindow(url,selectMenuNo);
		return;
	}


	var f = makeform(url);

	if(menuClass!=null){
		f.appendChild(AddData('menuClass', menuClass));
	}
	f.appendChild(AddData('paramMenuNo', menuNo));
	f.appendChild(AddData('selectMenuNo', selectMenuNo));
	f.submit();
}

/**
 * menu 이동 form을 참조하여 return
 *
 * @param ActionURL 이동 url
 * @returns document.frmMenuHandle
 */
function makeform(ActionURL) {
	var f = document.frmMenuHandle;
	f.action=ActionURL;
	f.method="post";
	f.target="";
	return f;
}

/**
 * input 태그 생성하여 return
 *
 * @param name name
 * @param value 변수값
 * @returns var i = document.createElement("input")
 */
function AddData(name, value) {
	var i = document.createElement("input");
	i.setAttribute("type", "hidden");
	i.setAttribute("name", name);
	i.setAttribute("value", value);
	return i;
}