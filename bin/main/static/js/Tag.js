
const xhr = new XMLHttpRequest()
xhr.onreadystatechange = function(){
	if (this.readyState == 4 && this.status == 200){
		const res = JSON.parse(xhr.response)
		const container = document.querySelector('.container')
		const myList = document.querySelector('.myList')
		
		console.log({res})
		
		res.forEach(function(category){
			const tagItem = document.createElement('div')
			
			const name = document.createElement('h2')
			name.innerHTML = `<a href="/category?id=${category.id}">${category.name}</a>`
			
			container.appendChild(tagItem)
			tagItem.appendChild(name)
		})
	}
}

xhr.open('GET', 'http://localhost:8080/tags', true)
xhr.send()