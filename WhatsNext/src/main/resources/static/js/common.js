/**
 * 
 */

function enableCollapsibleDiv() {
	var coll = document.getElementsByClassName('collapsible');
	var i;

	for (i = 0; i < coll.length; i++) {
		coll[i].addEventListener('click', function() {
			this.classList.toggle('collapse-active');
			var content = this.nextElementSibling;
			if (content.style.maxHeight) {
				content.style.maxHeight = null;
			} else {
				content.style.maxHeight = content.scrollHeight + 'px';
			}
		});
	}
}

function enablePagination(pageNum, totalPage) {
	if (totalPage > 1) {
		var pageBarDiv = document.getElementsByClassName('page-nav-bar')[0];
		for (i = Math.max(1, pageNum - 1); i <= Math.min(totalPage, pageNum + 1); i++) {
			pageBarDiv.children[i].removeAttribute('hidden');
		}
	}
}