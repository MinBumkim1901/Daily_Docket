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

patchEmailForm['sendNumber'].addEventListener('click', (e) => {
    e.preventDefault();
    // 이벤트 핸들러에서 폼 제출 방지

    if (patchEmailForm['email'].value === '') {
        alert('이메일을 입력해주세요');
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/user/findAccount/emailCheck?email=${patchEmailForm['email'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        alert('인증번호 전송을 실패했습니다! 재시도해주세요');
                        break;
                    case 'success':
                        alert('인증번호를 보냈으니 5분안에 확인해주세요!');
                        patchEmailForm['salt'].value = responseObject.salt;
                        patchEmailForm['email'].setAttribute('readOnly','readOnly');
                        patchEmailForm['sendNumber'].setAttribute('disabled','disabled');
                        patchEmailForm['code'].removeAttribute('disabled');
                        patchEmailForm['verifiedNumber'].removeAttribute('disabled');
                        // 폼 제출 함수 호출
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
});
patchEmailForm['verifiedNumber'].addEventListener('click', (e) => {
    e.preventDefault();
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', patchEmailForm['email'].value);
    formData.append('salt', patchEmailForm['salt'].value);
    formData.append('code', patchEmailForm['code'].value);
    xhr.open('PATCH', '/user/findAccount/emailCheck');
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE){
            if(xhr.status >= 200 && xhr.status<300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                       alert('인증번호가 틀렸습니다!');
                        break;
                    case 'success':
                        alert('인증성공!');
                        break;
                    default:
                        alert('서버오류');
                }
            } else {
                alert('서버오류');
            }
        }
    };
    xhr.send(formData);
})


patchEmailForm['changePassword'].addEventListener('click', (e) => {
    e.preventDefault();
    // 이벤트 핸들러에서 폼 제출 방지
});

