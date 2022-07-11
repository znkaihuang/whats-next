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
			if (content.style.maxHeight != '0px') {
				content.style.maxHeight = '0px';
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

function toggleSortIcon() {
	var sort = document.getElementsByClassName('sort');
	var i;

	for (i = 0; i < sort.length; i++) {
		sort[i].addEventListener('click', function() {
			var sort = document.getElementsByClassName('sort');
			var i;
			for (i = 0; i < sort.length; i++) {
				if (sort[i] != this) {
					if (sort[i].classList.contains('ascend')) {
						sort[i].classList.remove('ascend');
					}
					if (sort[i].classList.contains('descend')) {
						sort[i].classList.remove('descend');
					}
				}
			}
			var classList = this.classList;
			if (classList.contains('ascend')) {
				this.classList.remove('ascend');
				this.classList.add('descend');
			}
			else if (classList.contains('descend')) {
				this.classList.remove('descend');
			}
			else {
				this.classList.add('ascend');
			}
		});
	}
}