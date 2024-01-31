const searchEmailForm = document.getElementById('searchEmailForm');
const recoverPasswordForm = document.getElementById('recoverPasswordForm');
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

recoverPasswordForm['sendNumber'].addEventListener('click', (e) => {
    e.preventDefault();
    // 이벤트 핸들러에서 폼 제출 방지

    if (recoverPasswordForm['email'].value === '') {
        alert('이메일을 입력해주세요');
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET', `/user/findAccount/emailCheck?email=${recoverPasswordForm['email'].value}`);
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
                        recoverPasswordForm['salt'].value = responseObject.salt;
                        recoverPasswordForm['email'].setAttribute('readOnly','readOnly');
                        recoverPasswordForm['sendNumber'].setAttribute('disabled','disabled');
                        recoverPasswordForm['code'].removeAttribute('disabled');
                        recoverPasswordForm['verifiedNumber'].removeAttribute('disabled');
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
recoverPasswordForm['verifiedNumber'].addEventListener('click', (e) => {
    e.preventDefault();

    if(recoverPasswordForm['code'].value === ''){
        alert('인증번호를 적어주세요!');
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', recoverPasswordForm['email'].value);
    formData.append('salt', recoverPasswordForm['salt'].value);
    formData.append('code', recoverPasswordForm['code'].value);
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
                        recoverPasswordForm['code'].setAttribute('readOnly','readOnly');
                        recoverPasswordForm['verifiedNumber'].setAttribute('disabled','disabled');
                        recoverPasswordForm['password'].removeAttribute('disabled');
                        recoverPasswordForm['passwordCheck'].removeAttribute('disabled');
                        recoverPasswordForm['changePassword'].removeAttribute('disabled');
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


recoverPasswordForm['changePassword'].addEventListener('click', (e) => {
    e.preventDefault();
    if (recoverPasswordForm['password'].value === '') {
        alert('비밀번호를 입력해주세요');
        return;
    } if (recoverPasswordForm['passwordCheck'].value === '') {
        alert('비밀번호를 한번 더 입력해주세요');
        return;
    } if (recoverPasswordForm['password'].value !== recoverPasswordForm['passwordCheck'].value ) {
        alert('비밀번호가 일치하지 않습니다!');
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email',recoverPasswordForm['email'].value);
    formData.append('password',recoverPasswordForm['password'].value);
    xhr.open('PATCH', '/user/findAccount/ChangePassword');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseText === 'true') {
                    alert('비밀번호 변경에 성공했습니다!');
                    location.href = '/user/login';
                    return;
                } else if (xhr.responseText === 'false') {
                   alert('비밀번호 변경에 실패했습니다!');
                }
            } else {
                alert('통신');
            }

        }
    }
    xhr.send(formData);
    // 이벤트 핸들러에서 폼 제출 방지
});

