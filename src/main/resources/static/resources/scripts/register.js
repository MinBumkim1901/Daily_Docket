const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const registerForm = document.getElementById('registerForm');

signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});

registerForm.onsubmit = e => {
    e.preventDefault();
    if(registerForm['email'].value === ''){
        alert('이메일을 입력해주세요');
        return; //return을 통해서 함수 종료
    }
    if(registerForm['password'].value === ''){
        alert('비밀번호를 입력해주세요');
        return;
    }
    if(registerForm['name'].value === ''){
        alert('이름을 입력해주세요');
        return;
    }
    if(registerForm['nickname'].value ===''){
        alert('닉네임을 입력해주세요');
        return;
    }
    if(registerForm['birthday'].value === ''){
        alert('생일을 입력해주세요');
        return;
    }
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email',registerForm['email'].value);
    formData.append('password',registerForm['password'].value);
    formData.append('name',registerForm['name'].value);
    formData.append('nickname',registerForm['nickname'].value);
    formData.append('birthday',registerForm['birthday'].value);
    xhr.open('POST','/register');
    xhr.onreadystatechange = () => {
     if(xhr.readyState === XMLHttpRequest.DONE){
        if(xhr.status >= 200 && xhr.status<300) {
            const responseObject = JSON.parse(xhr.responseText);
            switch (responseObject.result) {
                case 'duplicate_email':
                    alert('중복된 이메일입니다!');
                    break;
                case 'duplicate_nickname':
                    alert('중복된 닉네임 입니다!');
                    break;
                case 'success':
                    alert('성공');
                    break;
                default:
                    alert('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.')
            }
        } else {
            alert('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.')
        }
     }
    };
    xhr.send(formData);
};