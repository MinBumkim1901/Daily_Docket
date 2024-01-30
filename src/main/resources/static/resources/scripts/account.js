const searchEmailForm = document.getElementById('searchEmailForm');
const patchEmailForm = document.getElementById('patchPasswordForm');
const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

searchEmailForm.onsubmit = e =>{
    e.preventDefault();
    //폼제출 방지

    if(searchEmailForm['name'].value === ''){
        alert('이름을 입력해주세요');
        return;
    }
    if(searchEmailForm['birth'].value ===''){
        alert('생일을 입력해주세요');
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET',`/user/findAccount/email?name=${searchEmailForm['name'].value}&birthStr=${searchEmailForm['birth'].value}`);
    xhr.onreadystatechange = () => {
     if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >= 200 && xhr.status<300) {
            const responseObject = JSON.parse(xhr.responseText);
            switch (responseObject.result) {
                case 'failed':
                    alert('존재하지 않는 유저입니다!');
                    break;
                case 'success':
                    alert(searchEmailForm['name'].value+'님의 이메일은 '+responseObject.email+' 입니다.');
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

patchEmailForm.onsubmit = e => {
    e.preventDefault();
    //폼 제출 방지

    if(patchEmailForm['email'].value === '') {
        alert('이메일을 입력해주세요');
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    xhr.open('GET', `/user/findAccount/passwordCheck?email=${patchEmailForm['email'].value}`);
    xhr.onreadystatechange = () => {
     if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >= 200 && xhr.status<300) {
        
        }else {
        
        }
       }
    };
    xhr.send(formData);

}