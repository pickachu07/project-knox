function main(params) {
	var result = params.value > 25000 ? true : false;
	return {
		isGreaterThan: result
	};
}

/*
	{
		"value": 30000
	}
*/