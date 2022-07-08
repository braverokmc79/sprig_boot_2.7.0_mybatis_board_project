let totalData; //총 데이터 수
let dataPerPage; //한 페이지에 나타낼 글 수
let pageCount; //페이징에 나타낼 페이지 수
let currentPage; //현재 페이지
let fileList = {
	
	init: function() {	
		this.list();	
	},

	list: function(page) {
		if (page == undefined || page == "") {
			page = 1;
		}

		fetch("/api/file/fileList?page=" + page)
		.then((response)=>response.json())
		.then(res=>{
			
			console.log(res.pageMaker);
			totalData = res.pageMaker.totalCount;
			dataPerPage = res.pageMaker.perPageNum;
			pageCount = res.pageMaker.displayPageNum;
			currentPage = res.pageMaker.page;

			if (res.resState == "success") {
				let html = "";
				res.data.forEach(function(item) {
					const regDate = item.regDate.replace('T', ' ').substring(0, 19);

					const ext=item.originalFilename.split('.').pop().toLowerCase();
					const ableExt=['jpg','png','jpeg','gif'];
					let img=`<span class="material-symbols-outlined" style="font-size:90px" >file_Open</span>`;
					if(ableExt.indexOf(ext) > -1) {
						if(item.thumbnailResourcePathname==null){
							img="썸네일 이미지 없음";
						}else{
							img=`<img  class="img-thumbnail rounded max-auto d-inline" src="${item.thumbnailResourcePathname}">`;
						}
						
					}

					html += `<tr>
			         <td>${item.uploadFileSeq}</td>
			         <td class="text-center">${img}</td>
			         <td>${item.boardSeq}</td>
			         <td>${item.boardType != null ? item.boardType.label : ""}</td>
			         <td>${item.filename}</td>
			         <td>${item.originalFilename}</td>                  
			         <td>${regDate}</td> 
			         <th><button class="btn btn-sm  btn-danger fileDeleteBtn" data-id="${item.uploadFileSeq}"  >삭제</button></th>   
			        </tr>  
			        `;
				});


				document.querySelector("tbody").innerHTML = html;
			}

			//페이징 표시 호출
			fileList.paging();
			
			//삭제 이벤트 추가
			let fileDeletes=document.querySelectorAll(".fileDeleteBtn");			
			for(let i=0; i<fileDeletes.length; i++){
				fileDeletes[i].addEventListener("click", function(e){
						fileList.fileDelete(e.target.getAttribute("data-id"));	
				});
			}
			
			
		}).catch(error=>console.log("error  :  {}", error));
		

	},


	//파일 삭제
	fileDelete:function(uploadFileSeq){			
			if(confirm("정말 삭제 하시겠습니까?")){
				console.log("파일 삭제 {} ",uploadFileSeq);	
				
				fetch("/api/file/fileDelate/"+uploadFileSeq,{
					method:"DELETE"		
				})
				.then((response)=>response.json())
				.then((res)=>{	
							
					if(res.resState==="success"){	
						currentPage=1;
						fileList.list();
					}						
				});			
				
			}
	},



	// 페이징 표시 함수 
	 //function(totalData, dataPerPage, pageCount, currentPage)
	paging: function(){
		console.log("currentPage : " + currentPage);

		totalPage = Math.ceil(totalData / dataPerPage); //총 페이지 수

		if (totalPage < pageCount) {
			pageCount = totalPage;
		}

		let pageGroup = Math.ceil(currentPage / pageCount); // 페이지 그룹
		let last = pageGroup * pageCount; //화면에 보여질 마지막 페이지 번호

		if (last > totalPage) {
			last = totalPage;
		}

		let first = last - (pageCount - 1); //화면에 보여질 첫번째 페이지 번호
		let next = last + 1;
		let prev = first - 1;

		let pageHtml = "";

		if (prev > 0) {
			pageHtml += "<li><a href='#' id='prev'> « </a></li>";
		}

		//페이징 번호 표시 
		for (var i = first; i <= last; i++) {

			if (currentPage == i) {
				pageHtml +=
					"<li class='on'><a href='#' id='" + i + "'>" + i + "</a></li>";
			} else {
				pageHtml += "<li><a href='#' id='" + i + "'>" + i + "</a></li>";
			}
		}


		if (last < totalPage) {
			pageHtml += "<li><a href='#' id='next'> » </a></li>";
		}


		document.querySelector("#pagingul").innerHTML = pageHtml;

		let displayCount = "";
		displayCount = "현재 1 - " + totalPage + " (" + currentPage + "페이지) / " + totalData + "건";
		document.querySelector("#displayCount").innerText = displayCount;


		//페이징 번호 클릭 이벤트 
		const paginationClass = document.querySelectorAll("#pagingul li a");
		for (let i = 0; i < paginationClass.length; i++) {
			paginationClass[i].addEventListener("click", function(e) {
				e.preventDefault();

				let $id = this.getAttribute("id")
				selectedPage = this.innerText;

				console.log("선택한 페이지 ", selectedPage);
				if ($id == "next") selectedPage = next;
				if ($id == "prev") selectedPage = prev;
				fileList.list(selectedPage);
			});
		}

	}

}


//파일 삭제
function fileDelete(uploadFileSeq){
	alert(uploadFileSeq);
}

fileList.init();




