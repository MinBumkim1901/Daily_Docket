const loginForm = document.getElementById('loginForm');

loginForm.onsubmit = e => {
    e.preventDefault(); //폼 제출방지

    if(loginForm['email'].value === ''){
        alert('이메일을 입력해주세요');
        return; //조건문 종료
    }

    if(loginForm['password'].value === ''){
        alert('비밀번호를 입력해주세요');
        return; //조건문 종료
    }


}