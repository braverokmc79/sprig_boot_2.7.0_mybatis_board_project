let fileUpload= {

	init:function(){
		document.querySelector("#uploadBtn1").addEventListener("click", function(event){
			fileUpload.uploadBtn1();	
		});
		
		document.querySelector("#uploadBtn2").addEventListener("click", function(event){
			fileUpload.uploadBtn2();	
		});
		
		document.querySelector("#uploadBtn3").addEventListener("click", function(event){
			fileUpload.uploadBtn3();	
		});
	},
	
	
	//1.파일 업로드 테스트
	uploadBtn1:function(){
		let formData = new FormData();
		const inputFile =document.getElementById("uploadFile1");		
		const files = inputFile.files;
		
		if(inputFile.value==undefined || inputFile.value==""){
			return;
		}
	
		//formdata에 파일 데이터 추가
		for(let i=0; i<files.length; i++){			
			if(!checkFileName(files[i].name, files[i].size)){
				console.log("file error");
				return false;
			}			
			formData.append("uploadFile", files[i]);
		}
		 	
		const params={
			method:"POST",
			body:formData
		} 	
		
		 	
		fetch('/api/file/save', params).then((response)=>response.json())
		.then((res)=>{	
			if(res.resState=="success"){					
					document.querySelector("#uploadFile1").value="";					
					document.querySelector("#image_container1").innerHTML="<textarea>"+JSON.stringify(res, null, 4) + "</textarea>";
					alert("업로드 처리 되었습니다.");
			}
		}).catch((error)=>{
			console.log("error:", error);
		})
		 		  	 		
	},
	
	
	
	//2.파일 업로드 테스트2
	uploadBtn2:function(){
		let formData = new FormData();
		const inputFile=document.getElementById("uploadFile2");
		const files = inputFile.files;
		if(inputFile.value==undefined || inputFile.value==""){
			return;
		}
		
		//formdata에 파일 데이터 추가
		for(let i=0; i<files.length; i++){			
			if(!checkFileName(files[i].name, files[i].size)){
				console.log("file error");
				return false;
			}			
			formData.append("uploadFile", files[i]);
		}
		 	
		 		
 		$.ajax({
 		    type:"POST",
 		    url : '/api/file/fileSave',
 		    processData: false,
 		    contentType: false,
 		    data: formData,
 		    success: function(res){
 		    	if(res.resState=="success"){
					document.querySelector("#uploadFile2").value="";
					document.querySelector("#image_container2").innerHTML="<textarea>"+JSON.stringify(res, null, 4) + "</textarea>";
					alert("업로드 처리 되었습니다.");
				}
 		    },
 		    error: function(err){
 		      console.log("err:", err)
 		    }
 		  }) 		 		
	}
	,
	
	//3.멀티 파일 업로드 테스트
	uploadBtn3:function(){
		let formData = new FormData();
		const inputFile = document.getElementById("uploadFile3");
		const files = inputFile.files;
		if(inputFile.value==undefined || inputFile.value==""){
			return;
		}
		//formdata에 파일 데이터 추가
		for(let i=0; i<files.length; i++){			
			if(!checkFileName(files[i].name, files[i].size)){
				console.log("file error");
				return false;
			}			
			formData.append("uploadFile", files[i]);
		}
		 
		 
		const params={
			method:"POST",
			body:formData
		} 	
			
		fetch('/api/file/multiFileSave', params).then((response)=>response.json())
		.then((res)=>{	
			if(res.resState=="success"){					
					document.querySelector("#uploadFile3").value="";					
					document.querySelector("#image_container3").innerHTML="<textarea>"+JSON.stringify(res, null, 4) + "</textarea>";
					alert("업로드 처리 되었습니다.");
			}
		}).catch((error)=>{
			console.log("error:", error);
		})		 
		 		
 	}

	
}



//파일 확장자 체크 및 사이즈 체크
function checkFileName(str, fileSize){		 
	
	//1. 확장자 체크
    const ext =  str.split('.').pop().toLowerCase();
    const ableExts=['bmp' , 'hwp', 'jpg', 'pdf', 'png', 'xls', 'zip', 'pptx', 'xlsx', 'jpeg', 'doc', 'gif' ,'mp4'];
    
    
    if( ableExts.indexOf(ext) ==-1  ) {	 
        //alert(ext);
        alert(ext+' 파일은 업로드 하실 수 없습니다.');
        return false;
    }	 
    //2. 파일명에 특수문자 체크
    const pattern =   /[\{\}\/?,;:|*~`!^\+<>@\#$%&\\\=\'\"]/gi;
    if(pattern.test(str) ){
        //alert("파일명에 허용된 특수문자는 '-', '_', '(', ')', '[', ']', '.' 입니다.");
        alert('파일명에 특수문자를 제거해주세요.');
        return false;
    }
    
	const maxSize = 1024*1024*17; //517MB
	if(fileSize >= maxSize){
		alert("파일 사이즈 초과");
		return false;
	}		
	return true;	
}

//이미지 미리보기
function setThumbnail(event) {
	const dataView=$(event.target).attr("data-view");
	
	document.querySelector("div#"+dataView).innerHTML="";    
    for (let image of event.target.files) {    	
    	// 확장자 체크
        const ext =  image.name.split('.').pop().toLowerCase();
        const ableExt=['jpg','png','jpeg','gif'];
        if(ableExt.indexOf(ext) > -1) {	
        	//console.log("이미지");
        	
       	  const reader = new FileReader();
       		
   	      reader.onload = function(event) {
   	        let img = document.createElement("img");
   	        img.setAttribute("src", event.target.result);
   	     	img.style.width="auto";
   	        img.style.height="100px";
   	        document.querySelector("div#"+dataView).appendChild(img);
   	      };
   	
   	      reader.readAsDataURL(image);    	                
        }	    		    
    }
}



fileUpload.init();




