import {getJson, deleteJson, postJson, putJson, restApi} from "./utilsNet.js";

const URL = 'api/v1';

const getBooksWithUrl = () => {
  return getJson(`${URL}/book/all`)
};
const deleteBooksWithUrl = (id) => {
  return deleteJson(`${URL}/book/${id}`)
};
const createBooksWithUrl = (data) => {
  return postJson(`${URL}/book/`, data)
};
const updateBooksWithUrl = (data) => {
  return putJson(`${URL}/book/${data.id}`, data)
};
const removeChildren = node => {
  while (node.firstChild) {
    node.removeChild(node.lastChild);
  }
}

const promptForBook = () => {
  let name = prompt('Введите название книги'),
    isbnCode = prompt('Введите код ISBN'),
    publicationYear = prompt('Введите год издания'),
    author = prompt('Введите автора'),
    genre = prompt('Введите жанр');
  return {
    name: name,
    isbnCode: isbnCode,
    publicationYear: publicationYear,
    authorList: [
      {
        name: author
      }
    ],
    genreList: [
      {
        name: genre
      }
    ]
  }
}
const createBook = () => {
  let book = promptForBook();
  restApi(createBooksWithUrl, book)
    .then(() => renderBooksTable())
    .catch(err => console.log(err));
}
const updateBook = (id) => {
  let book = {
    id: id,
    ...promptForBook()
  }
  let isConfirmed = confirm('Изменить?');
  if (isConfirmed) {
    restApi(updateBooksWithUrl, book)
      .then(() => renderBooksTable())
      .catch(err => console.log(err));
  }
}

const renderBooksTable = () => {
  restApi(getBooksWithUrl).then(books => {
    let tableBody = document.getElementById('books-table-body');
    removeChildren(tableBody);
    createTable(books, tableBody);
  })
    .catch(err => console.log(err));
  ;
}

function createTable(books, tableBody) {
  books.forEach(book => {
    let props = [
      book.name,
      book.isbnCode,
      book.publicationYear,
      book.authorList.reduce((acc, curValue) => `${curValue.name} ${acc}`, ''),
      book.genreList.reduce((acc, curValue) => `${curValue.name} ${acc}`, '')
    ]
    tableBody.appendChild(createRow(props, book.id));
  })
  let button = document.createElement('button');
  button.onclick = createBook;
  button.innerText = 'Создать';
  tableBody.appendChild(button);
}

function createRow(props, id) {
  let row = document.createElement('tr');
  let td;
  props.forEach(prop => {
    td = document.createElement('td');
    td.innerText = prop;
    row.appendChild(td);
  });
  let deleteButton = document.createElement('button');
  deleteButton.onclick = () => {
    let isConfirmed = confirm('Точно удалить?');
    if (isConfirmed) {
      restApi(deleteBooksWithUrl, id)
        .then(() => renderBooksTable())
        .catch(err => console.log(err));
    }
  };
  deleteButton.innerText = 'Удалить';
  row.appendChild(deleteButton);
  let updateButton = document.createElement('button');
  updateButton.onclick = () => {
    updateBook(id);
  };
  updateButton.innerText = 'Изменить';
  row.appendChild(updateButton);
  return row;
}

renderBooksTable();