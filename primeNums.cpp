#include <iostream>
using namespace std;

#define LIMIT 100

/******************************************************************************
* Program to display all prime numbers between one & LIMIT
******************************************************************************/
int main()
{
	bool prime;

	// for every # from 1 to LIMIT...
	for (int i = 1; i <= LIMIT; i++)
	{
		prime = true; // prime by default

		// check if divisible by anything other than 1 & that #...
		for (int j = 2; j < i; j++)
		{
			// if so, it ain't prime.
			if (i % j == 0)
			{
				prime = false;
				break;
			}
		}

		if (prime)
			cout << i << " ";
	}
	cout << endl;

	return 0;
}
