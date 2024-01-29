const emailForm = document.getElementById('emailForm');

emailForm.onsubmit = e =>{
    e.preventDefault();
    //폼제출 방지
    
    if(emailForm['name'].value === ''){
        alert('이름을 입력해주세요');
        return;
    }
    if(emailForm['birth'].value ===''){
        alert('생일을 입력해주세요');
        return;
    }
    
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('name',emailForm['name'].value);
    formData.append('birth',emailForm['birth'].value);
    xhr.open('GET','/emailFind');
    xhr.onreadystatechange = () => {
     if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >= 200 && xhr.status<300) {
        
        }else {
        
        }
       }
    };
    xhr.send(formData);
    
}