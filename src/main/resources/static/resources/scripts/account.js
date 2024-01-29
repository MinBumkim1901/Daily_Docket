const SearchEmailForm = document.getElementById('SearchEmailForm');

SearchEmailForm.onsubmit = e =>{
    e.preventDefault();
    //폼제출 방지
    
    if(SearchEmailForm['name'].value === ''){
        alert('이름을 입력해주세요');
        return;
    }
    if(SearchEmailForm['birth'].value ===''){
        alert('생일을 입력해주세요');
        return;
    }
    
    const xhr = new XMLHttpRequest();
    xhr.open('GET',`/user/findAccount/email?name=${SearchEmailForm['name'].value}&birthStr=${SearchEmailForm['birth'].value}`);
    xhr.onreadystatechange = () => {
     if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >= 200 && xhr.status<300) {
            const responseObject = JSON.parse(xhr.responseText);
            switch (responseObject.result) {
                case 'name_failed':
                    alert('존재하지 않는 이름입니다.');
                    break;
                case 'birth_failed':
                    alert('존재하지 않는 생일입니다');
                break;
                case 'failed':
                    alert('존재하지 않음!');
                    break;
                case 'success':
                    alert(SearchEmailForm['name'].value+'님의 이메일은 '+responseObject.email+' 입니다.');
                    break;
                default:
                    alert('서버오류');
            }
        } else {
            alert('서버오류');
        }
     }
    };
    xhr.send();
};