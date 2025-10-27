// 간결한 방식의 document.getElementById()
function getId(name) {
  nameValid(name);
  const target = document.getElementById(name);
  return target;
}

// 간결한 방식의 document.getElementsByClassName()
// 파라미터로 두 번째에 index를 주면 index에 해당하는 순서의 class 태그를 가져옴
function getClass(name, index = 0) {
  nameValid(name);
  const target = document.getElementsByClassName(name)[index];
  return target;
}

// addEventListener를 더 간결한 방식으로 사용하는 함수
function addEvent(tag, name, callback) {
  nameValid(name);
  let target = tag;
  if(target instanceof HTMLElement) {
    target.addEventListener(name, (event) => {
      callback(event);
    });
  }
}

// 태그에 class 할당
/*
EX) 사용법
const test = getId('test');
addClassList(test, 'orange')
addClassList(test, 'red')
addClassList(test, 'white');

result : <div id="test" class="orange red white">테스트</div>
*/
function addClassList(tag, name) {
  nameValid(name);
  const target = tag;
  if(target instanceof HTMLElement) {
    target.classList.add(name);
  }
}

// 태그에 class 제거
/*
EX) 사용법
const test = getId('test'); // class="orange white red" 있다고 가정
delClassList(test, 'white')
delClassList(test, 'orange');

result: <div id="test" class="red">테스트</div>
*/
function delClassList(tag, name) {
  nameValid(name);
  const target = tag;
  if(target instanceof HTMLElement) {
    target.classList.remove(name);
  }
}

// 파라미터 검증 함수
function nameValid(name) {
  if(!name) throw new Error(`전달된 파라미터가 null or undefined or 빈 객체입니다. : ${name}`);
}

// 단 한줄로 이벤트 할당까지 끝내는 객체 함수
const TAG = {
  /*
  EX) 사용법
    const test = TAG.getId('test', 'click', (e) => {
      e.stopPropagation();
      e.preventDefault();
    })
  */
  getId(name, event, callback) {
    const target = getId(name);

    if(event && callback) {
      addEvent(target, event, callback);
    }

    return target;
  },
  /*
  EX) 사용법
    const test = TAG.getClass('test', 'click', (e) => {
      e.stopPropagation();
      e.preventDefault();
    }, 2)
  */
  getClass(name, event, callback, index = 0) {
    const target = getClass(name, index);

    if(event && callback) {
      addEvent(target, event, callback);
    }

    return target;
  }
}

function $Tag(tag) {
  if(!(tag instanceof HTMLElement)) {
    throw new Error('$Tag Error >>> HTMLElement Only');
  }
  let bubbleDisable = false;

  return {
    tag,
    addClass(name) {
      nameValid(name);
      this.tag.classList.add(name);
      return this;
    },
    removeClass(name) {
      nameValid(name);
      this.tag.classList.remove(name);
      return this;
    },
    on(name, callback) {
      nameValid(name);
      if(callback) {
        this.tag.addEventListener(name, callback);
      }
      return this;
    },
    blockBubble(bool = true) {
      if(bool && !bubbleDisable) {
        this.tag.addEventListener('click', (e) => {
          e.stopPropagation();
        });
        bubbleDisable = true;
      }
      return this;
    }
  }
}