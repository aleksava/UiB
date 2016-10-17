; Program som skriver ut Hello World
; Konstanter

cr equ 13 ; Vognretur
lf equ 10 ; Linjeskift

section .data ; Datasegment
	melding db 'Hello World!',cr,lf
	lengde equ $ - melding

section .text 			; Kodesegment
	global _start 		; must be declared for linker (ld)

_start:					; tells linker entry point
	mov edx,lengde		; message length
	mov ecx,melding 	; message to write
	mov ebx,1			; file descriptor (stdout)
	mov eax,4 			; system call number (sys_write)
	int 80h				; call kernel
	
	mov ebx,0			
	mov eax,1			; system call number (sys_exit)
	int 80h
	
	
