const loginForm = document.getElementById('loginForm');

loginForm.onsubmit = e => {
    e.preventDefault(); //폼 제출방지

    if(loginForm['email'].value === ''){
        alert('이메일을 입력해주세요');
        return; //조건문 종료
    }

    if(loginForm['password'].value === ''){
        alert('비밀번호를 입력해주세요');
        alert('비밀번호를 입력해주세요');
        return; //조건문 종료
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', loginForm['email'].value);
    formData.append('password', loginForm['password'].value);
    xhr.open('POST', `/login`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseText === 'true') {
                        location.href = '/';
                } else if(xhr.responseText === 'false') {
                    alert('로그인이 실패했습니다!');
                }
            } else {
                alert('오류 발생');
            }

        }
    }
    xhr.send(formData);
}